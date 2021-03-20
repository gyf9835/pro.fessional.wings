package pro.fessional.wings.slardar.spring.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.session.DefaultCookieSerializerCustomizer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import pro.fessional.wings.slardar.session.WingsSessionIdResolver;
import pro.fessional.wings.slardar.spring.prop.SlardarEnabledProp;

/**
 * 通过 session-hazelcast.xml 配置好 spring session用的map，主要是index和serial
 * https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-session
 * https://docs.spring.io/spring-session/docs/2.4.2/reference/html5/#spring-security
 * https://guides.hazelcast.org/spring-session-hazelcast/
 *
 * @author trydofor
 * @since 2019-06-26
 */
@Configuration
@ConditionalOnProperty(name = SlardarEnabledProp.Key$session, havingValue = "true")
@EnableConfigurationProperties(ServerProperties.class)
public class SlardarSessionConfiguration {

    private static final Log logger = LogFactory.getLog(SlardarSessionConfiguration.class);

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        logger.info("Wings conf httpSessionEventPublisher");
        return new HttpSessionEventPublisher();
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver(
            ServerProperties serverProperties,
            ObjectProvider<CookieSerializer> cookieSerializer,
            ObjectProvider<DefaultCookieSerializerCustomizer> cookieSerializerCustomizers) {
        Session.Cookie cookie = serverProperties.getServlet().getSession().getCookie();
        final String cookieName = cookie.getName();
        logger.info("Wings conf httpSessionIdResolver by server.servlet.session.cookie.name=" + cookieName);

        CookieSerializer serializer = cookieSerializer.getIfAvailable();
        if (serializer == null) {
            DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from(cookieName).to(defaultCookieSerializer::setCookieName);
            map.from(cookie::getDomain).to(defaultCookieSerializer::setDomainName);
            map.from(cookie::getPath).to(defaultCookieSerializer::setCookiePath);
            map.from(cookie::getHttpOnly).to(defaultCookieSerializer::setUseHttpOnlyCookie);
            map.from(cookie::getSecure).to(defaultCookieSerializer::setUseSecureCookie);
            map.from(cookie::getMaxAge).to((maxAge) -> defaultCookieSerializer.setCookieMaxAge((int) maxAge.getSeconds()));
            cookieSerializerCustomizers.orderedStream().forEach((customizer) -> customizer.customize(defaultCookieSerializer));
            serializer = defaultCookieSerializer;
        }

        final HeaderHttpSessionIdResolver headerHttpSessionIdResolver = new HeaderHttpSessionIdResolver(cookieName);
        final CookieHttpSessionIdResolver cookieHttpSessionIdResolver = new CookieHttpSessionIdResolver();
        cookieHttpSessionIdResolver.setCookieSerializer(serializer);

        return new WingsSessionIdResolver(headerHttpSessionIdResolver, cookieHttpSessionIdResolver);
    }
}
