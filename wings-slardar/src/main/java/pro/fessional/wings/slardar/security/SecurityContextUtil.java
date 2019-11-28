package pro.fessional.wings.slardar.security;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import pro.fessional.mirana.cast.TypedCastUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Wrapper fo SecurityContextHolder
 *
 * @author trydofor
 * @since 2019-07-09
 */
public class SecurityContextUtil {
    private SecurityContextUtil() {
    }

    /**
     * 获得终端信息
     *
     * @return context
     */
    @Nullable
    public static WingsTerminalContext.Context getTerminalContext() {
        return WingsTerminalContext.get();
    }

    /**
     * 获得 OAuth2x的Context
     *
     * @return context
     */
    @Nullable
    public static WingsOAuth2xContext.Context getOauth2xContext() {
        WingsOAuth2xContext.Context wtx = WingsOAuth2xContext.get();
        if (wtx == null) {
            Authentication a = SecurityContextHolder.getContext().getAuthentication();
            if (a instanceof OAuth2Authentication) {
                OAuth2Authentication oau = (OAuth2Authentication) a;
                Map<String, String> param = oau.getOAuth2Request().getRequestParameters();
                wtx = WingsOAuth2xContext.set(param);
            }
        }
        return wtx;
    }

    /**
     * 如果是OAuth2Authentication则返回，否则null
     *
     * @return 对象
     */
    @Nullable
    public static OAuth2Authentication getOAuth2Authentication() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a instanceof OAuth2Authentication) {
            return (OAuth2Authentication) a;
        } else {
            return null;
        }
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static Collection<GrantedAuthority> getAuthorities() {
        Authentication atn = SecurityContextHolder.getContext().getAuthentication();
        if (atn == null) return Collections.emptyList();

        Collection<? extends GrantedAuthority> ats = atn.getAuthorities();
        if (ats == null) {
            return Collections.emptyList();
        }
        return (Collection<GrantedAuthority>) ats;
    }

    @NotNull
    public static <T extends GrantedAuthority> Collection<T> getAuthorities(Class<T> claz) {
        Authentication atn = SecurityContextHolder.getContext().getAuthentication();
        if (atn == null) return Collections.emptyList();
        return TypedCastUtil.castCollection(atn.getAuthorities(), claz);

    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getDetails() {
        Authentication atn = SecurityContextHolder.getContext().getAuthentication();
        if (atn == null) return null;
        return (T) atn.getDetails();
    }

    @Nullable
    public static <T> T getDetails(Class<T> claz) {
        Authentication atn = SecurityContextHolder.getContext().getAuthentication();
        if (atn == null) return null;

        return TypedCastUtil.castObject(atn.getDetails(), claz);
    }

    /**
     * 一般为 UserDetailsService 放入的 UserDetails
     *
     * @param <T> UserDetails 类型
     * @return UserDetails
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getPrincipal() {
        Authentication atn = SecurityContextHolder.getContext().getAuthentication();
        if (atn == null) return null;
        return (T) atn.getPrincipal();
    }

    /**
     * 一般为 UserDetailsService 放入的 UserDetails
     *
     * @param <T> UserDetails 类型
     * @return UserDetails
     */
    @Nullable
    public static <T> T getPrincipal(Class<T> claz) {
        Authentication atn = SecurityContextHolder.getContext().getAuthentication();
        if (atn == null) return null;
        return TypedCastUtil.castObject(atn.getPrincipal(), claz);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getCredentials() {
        Authentication atn = SecurityContextHolder.getContext().getAuthentication();
        if (atn == null) return null;
        return (T) atn.getCredentials();
    }

    @Nullable
    public static <T> T getCredentials(Class<T> claz) {
        Authentication atn = SecurityContextHolder.getContext().getAuthentication();
        if (atn == null) return null;
        return TypedCastUtil.castObject(atn.getCredentials(), claz);
    }
}
