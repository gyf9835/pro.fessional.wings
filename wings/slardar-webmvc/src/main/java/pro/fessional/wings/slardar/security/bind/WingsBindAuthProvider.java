package pro.fessional.wings.slardar.security.bind;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import pro.fessional.mirana.bits.MdHelp;
import pro.fessional.wings.slardar.security.PasssaltEncoder;
import pro.fessional.wings.slardar.security.PasswordHelper;
import pro.fessional.wings.slardar.security.WingsAuthCheckService;
import pro.fessional.wings.slardar.security.WingsAuthDetails;
import pro.fessional.wings.slardar.security.WingsUserDetails;
import pro.fessional.wings.slardar.security.WingsUserDetailsService;
import pro.fessional.wings.slardar.security.impl.DefaultWingsAuthDetails;
import pro.fessional.wings.slardar.security.pass.DefaultPasssaltEncoder;

/**
 * 兼容DaoAuthenticationProvider，可替换之。如果设置onlyWingsBindAuthnToken=true，则只处理 WingsBindAuthnToken。
 * 需要注意的是，WingsBindAuthnToken 继承UsernamePasswordAuthenticationToken，可能会被其他Provider处理。
 * 不能继承DaoAuthenticationProvider，因为final retrieveUser，但mitigateAgainstTimingAttack很好，全部复制。
 *
 * @author trydofor
 * @since 2021-02-08
 */
public class WingsBindAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    private final static Log log = LogFactory.getLog(WingsBindAuthProvider.class);

    private static final String BadCredentialsCode = "AbstractUserDetailsAuthenticationProvider.badCredentials";
    private static final String USER_NOT_FOUND_PASSWORD = "TimingAttackProtectionUserNotFoundPassword";

    private volatile String userNotFoundEncodedPassword = null;
    private boolean onlyWingsBindAuthnToken = false;

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private PasssaltEncoder passsaltEncoder;
    private UserDetailsPasswordService userDetailsPasswordService;
    private WingsAuthCheckService wingsAuthCheckService;

    public WingsBindAuthProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.passsaltEncoder = new DefaultPasssaltEncoder(MdHelp.sha256);
    }

    // BGN DaoAuthenticationProvider

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        final boolean isWingsUserDetails = userDetails instanceof WingsUserDetails;
        if (!isWingsUserDetails || !((WingsUserDetails) userDetails).isPreAuthed()) {
            checkPassword(userDetails, authentication);
        }

        if (wingsAuthCheckService != null && isWingsUserDetails && authentication instanceof WingsBindAuthToken) {
            if (!wingsAuthCheckService.check((WingsUserDetails) userDetails, (WingsBindAuthToken) authentication)) {
                log.debug("Failed to post check userDetails and authentication");
                throw new BadCredentialsException(messages.getMessage(BadCredentialsCode, "Bad credentials"));
            }
        }
    }

    @Override // same
    protected void doAfterPropertiesSet() {
        Assert.notNull(userDetailsService, "A UserDetailsService must be set");
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        prepareTimingAttackProtection();
        try {
            final UserDetails userDetails;

            if (userDetailsService instanceof WingsUserDetailsService && authentication instanceof WingsBindAuthToken) {
                WingsUserDetailsService winUds = (WingsUserDetailsService) userDetailsService;
                WingsBindAuthToken winTkn = (WingsBindAuthToken) authentication;
                userDetails = buildUserDetails(username, winUds, winTkn);
            }
            else {
                userDetails = userDetailsService.loadUserByUsername(username);
            }

            if (userDetails == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            }
            return userDetails;
        }
        catch (UsernameNotFoundException ex) {
            mitigateAgainstTimingAttack(authentication);
            throw ex;
        }
        catch (InternalAuthenticationServiceException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    @Override // same
    protected Authentication createSuccessAuthentication(Object principal, Authentication authn, UserDetails details) {
        if (userDetailsPasswordService != null && passwordEncoder.upgradeEncoding(details.getPassword())) {
            String presentedPassword = presentPassword(details, authn);
            String newPassword = passwordEncoder.encode(presentedPassword);
            details = userDetailsPasswordService.updatePassword(details, newPassword);
        }

        return super.createSuccessAuthentication(principal, authn, details);
    }

    // END DaoAuthenticationProvider

    @Override
    public boolean supports(Class<?> authentication) {
        if (onlyWingsBindAuthnToken) {
            return WingsBindAuthToken.class.isAssignableFrom(authentication);
        }
        else {
            return super.supports(authentication);
        }
    }

    //
    @NotNull
    protected UserDetails buildUserDetails(String username, WingsUserDetailsService winUds, WingsBindAuthToken winTkn) {
        final UserDetails userDetails;
        final Object obj = winTkn.getDetails();
        final WingsAuthDetails winAdt;
        if (obj instanceof WingsAuthDetails) {
            winAdt = (WingsAuthDetails) obj;
        }
        else {
            log.debug("WARN No-WingsAuthDetails-In-WingsUserDetailsService-And-WingsBindAuthToken");
            winAdt = new DefaultWingsAuthDetails(obj);
        }
        userDetails = winUds.loadUserByUsername(username, winTkn.getAuthType(), winAdt);
        return userDetails;
    }

    protected void checkPassword(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
        if (authentication.getCredentials() == null) {
            log.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(messages.getMessage(BadCredentialsCode, "Bad credentials"));
        }

        String presentedPassword = presentPassword(userDetails, authentication);

        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            log.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(messages.getMessage(BadCredentialsCode, "Bad credentials"));
        }
    }

    protected String presentPassword(UserDetails details, Authentication auth) {
        String presentedPassword = auth.getCredentials().toString();
        // 加盐处理
        if (passsaltEncoder != null && details instanceof WingsUserDetails) {
            PasswordHelper helper = new PasswordHelper(passwordEncoder, passsaltEncoder);
            presentedPassword = helper.salt(presentedPassword, ((WingsUserDetails) details).getPasssalt());
        }
        return presentedPassword;
    }

    protected void prepareTimingAttackProtection() {
        if (userNotFoundEncodedPassword == null) {
            userNotFoundEncodedPassword = passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
        }
    }

    protected void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
        if (authentication.getCredentials() != null) {
            String presentedPassword = authentication.getCredentials().toString();
            passwordEncoder.matches(presentedPassword, userNotFoundEncodedPassword);
        }
    }

    // setter & getter


    public PasssaltEncoder getPasssaltEncoder() {
        return this.passsaltEncoder;
    }

    public void setPasssaltEncoder(PasssaltEncoder passsaltEncoder) {
        this.passsaltEncoder = passsaltEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;
        this.userNotFoundEncodedPassword = null;
    }

    public PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        Assert.notNull(passwordEncoder, "UserDetailsService cannot be null");
        this.userDetailsService = userDetailsService;
    }

    public UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }

    public void setUserDetailsPasswordService(UserDetailsPasswordService userDetailsPasswordService) {
        this.userDetailsPasswordService = userDetailsPasswordService;
    }

    public UserDetailsPasswordService getUserDetailsPasswordService() {
        return userDetailsPasswordService;
    }

    public boolean isOnlyWingsBindAuthnToken() {
        return onlyWingsBindAuthnToken;
    }

    public void setOnlyWingsBindAuthnToken(boolean onlyWingsBindAuthnToken) {
        this.onlyWingsBindAuthnToken = onlyWingsBindAuthnToken;
    }

    public WingsAuthCheckService getWingsAuthCheckService() {
        return wingsAuthCheckService;
    }

    public void setWingsAuthCheckService(WingsAuthCheckService wingsAuthCheckService) {
        this.wingsAuthCheckService = wingsAuthCheckService;
    }
}
