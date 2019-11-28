package pro.fessional.wings.slardar.spring.bean;

import lombok.Data;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.Assert;
import pro.fessional.mirana.code.LeapCode;
import pro.fessional.wings.slardar.security.JdkSerializationStrategy;
import pro.fessional.wings.slardar.security.WingsTokenEnhancer;
import pro.fessional.wings.slardar.security.WingsTokenStore;
import pro.fessional.wings.slardar.servlet.WingsFilterOrder;
import pro.fessional.wings.slardar.servlet.WingsOAuth2xFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author trydofor
 * @since 2019-07-09
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.wings.slardar.oauth2x", name = "enabled", havingValue = "true")
public class WingsOAuth2xConfiguration {

    private final Log logger = LogFactory.getLog(WingsOAuth2xConfiguration.class);

    @Bean
    @ConfigurationProperties("wings.slardar.security")
    public Security wingsOAuth2xConfigurationSecurity() {
        return new Security();
    }

    /**
     * #{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
     * #{noop}password
     * #{pbkdf2}5d923b44a6d129f3ddf3e3c8d29412723dcbde72445e8ef6bf3b508fbf17fa4ed4d6b99ca763d8dc
     * #{scrypt}$e0801$8bWJaSu2IKSn9Z9kM+TPXfOc/9bdYSrN1oD9qfVThWEwdRTnO7re7Ei+fUZRJ68k9lTyuTeUp4of4g24hHnazw==$OAOec05+bXxvuu/1qZ6NUR+xQYvYv7BeL1QxwRpY5Pc=
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder(@Value("${wings.slardar.security.password-encoder}") String encoder) {
        logger.info("Wings conf PasswordEncoder bean");
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        Assert.isTrue(encoders.containsKey(encoder), "unsupported encoder: " + encoder);
        return new DelegatingPasswordEncoder(encoder, encoders);
    }

    @Bean
    @ConditionalOnMissingBean(TokenStore.class)
    public TokenStore tokenStore() {
        logger.info("Wings conf InMemoryTokenStore to WingsTokenStore");
        WingsTokenStore store = new WingsTokenStore();
        store.addStore(new InMemoryTokenStore());
        return store;
    }

    @Configuration
    @ConditionalOnClass(RedisConnectionFactory.class)
    @Order(Ordered.LOWEST_PRECEDENCE - 100)
    public class Redis {
        @Autowired
        public void tokenStore(TokenStore tokenStore, RedisConnectionFactory factory) {
            if (tokenStore instanceof WingsTokenStore) {
                logger.info("Wings conf RedisTokenStore to WingsTokenStore");
                RedisTokenStore redis = new RedisTokenStore(factory);
                redis.setSerializationStrategy(new JdkSerializationStrategy());
                ((WingsTokenStore) tokenStore).addStore(redis);
            }
        }
    }

    @Bean
    @ConditionalOnProperty(prefix = "wings.slardar.actoken", name = "wings-enhance", havingValue = "true")
    public WingsTokenEnhancer tokenEnhancer(Security security, LeapCode leapCode) {
        logger.info("Wings conf WingsTokenEnhancer");
        WingsTokenEnhancer enhancer = new WingsTokenEnhancer();
        enhancer.setThirdTokenKey(security.getThirdTokenKey());
        enhancer.setWingsPrefix(security.getWingsPrefix());
        enhancer.setLeapCode(leapCode);
        return enhancer;
    }

    @Bean
    @ConfigurationProperties("wings.slardar.oauth2x")
    public WingsOAuth2xFilter.Config wingsOAuth2exFilterConfig() {
        return new WingsOAuth2xFilter.Config();
    }

    @Bean
    public WingsOAuth2xFilter wingsOAuth2exFilter(WingsOAuth2xFilter.Config config) {
        logger.info("Wings conf OAuth2x filter");
        WingsOAuth2xFilter filter = new WingsOAuth2xFilter(config);
        filter.setOrder(WingsFilterOrder.OAUTH2X);
        return filter;
    }

    @Bean
    @Lazy
    public Helper wingsOAuth2xConfigurationHelper() {
        return new Helper();
    }

    @Setter(onMethod = @__({@Autowired}))
    public static class Helper {

        private TokenStore tokenStore;
        private WingsOAuth2xFilter.Config config;
        private AuthenticationManager authenticationManager;
        private UserDetailsService userDetailsService;
        private PasswordEncoder passwordEncoder;
        private ObjectProvider<TokenEnhancer> tokenEnhancer;

        public ClientDetailsServiceConfigurer configure(ClientDetailsServiceConfigurer clients) throws Exception {
            InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
            for (WingsOAuth2xFilter.Client c : config.getClient().values()) {
                String secret = passwordEncoder.encode(c.getClientSecret());
                builder.withClient(c.getClientId())
                       .secret(secret)
                       .redirectUris(c.getRedirectUri())
                       .authorizedGrantTypes(c.getGrantType())
                       .scopes(c.getScope())
                       .accessTokenValiditySeconds(config.getAccessTokenLive())
                       .refreshTokenValiditySeconds(config.getRefreshTokenLive())
                       .autoApprove(c.isAutoApprove())
                ;
            }
            return clients;
        }

        public AuthorizationServerEndpointsConfigurer configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints.authenticationManager(authenticationManager)
                     .userDetailsService(userDetailsService)
                     .tokenStore(tokenStore)
                     .tokenEnhancer(tokenEnhancer.getIfAvailable())
            ;
            return endpoints;
        }

        public AuthorizationServerSecurityConfigurer configure(AuthorizationServerSecurityConfigurer security) {
            security.allowFormAuthenticationForClients()
                    .passwordEncoder(passwordEncoder)
                    .tokenKeyAccess("permitAll()")
                    .checkTokenAccess("isAuthenticated()")
            ;
            return security;
        }

        public HttpSecurity configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers("/oauth/**", "/error").permitAll()
                .antMatchers("/login", "/logout").permitAll()
            ;
            return http;
        }

        public ResourceServerSecurityConfigurer configure(ResourceServerSecurityConfigurer resources) {
            resources.tokenStore(tokenStore)
            ;
            return resources;
        }
    }

    @Data
    public static class Security {

        /**
         * 是否启用 wings token，是下2项的开关
         */
        private boolean wingsEnhance = true;
        /**
         * wing下access_token前缀，用以区分
         */
        private String wingsPrefix = "WG-";
        /**
         * 第三方token的parameter key
         */
        private String thirdTokenKey = "access_token_3rd";
    }
}