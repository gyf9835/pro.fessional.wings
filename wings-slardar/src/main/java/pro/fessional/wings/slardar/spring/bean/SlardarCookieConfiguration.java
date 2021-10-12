package pro.fessional.wings.slardar.spring.bean;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.fessional.wings.slardar.servlet.WingsServletConst;
import pro.fessional.wings.slardar.servlet.cookie.WingsCookieFilter;
import pro.fessional.wings.slardar.servlet.cookie.WingsCookieInterceptor;
import pro.fessional.wings.slardar.servlet.cookie.impl.WingsCookieInterceptorImpl;
import pro.fessional.wings.slardar.spring.prop.SlardarCookieProp;
import pro.fessional.wings.slardar.spring.prop.SlardarEnabledProp;

import java.util.Map;
import java.util.Set;

import static pro.fessional.wings.slardar.servlet.cookie.WingsCookieInterceptor.Coder.Aes;
import static pro.fessional.wings.slardar.servlet.cookie.WingsCookieInterceptor.Coder.B64;
import static pro.fessional.wings.slardar.servlet.cookie.WingsCookieInterceptor.Coder.Nop;

/**
 * @author trydofor
 * @since 2021-10-07
 */
@Configuration
@ConditionalOnProperty(name = SlardarEnabledProp.Key$cookie, havingValue = "true")
@RequiredArgsConstructor
public class SlardarCookieConfiguration {

    private static final Log logger = LogFactory.getLog(SlardarCookieConfiguration.class);

    private final SlardarCookieProp slardarCookieProp;

    @Bean
    public WingsCookieInterceptor wingsCookieInterceptor() {
        final String aesKey = slardarCookieProp.getAesKey();
        if (aesKey != null && aesKey.length() > 5) {
            logger.info("Wings conf WingsCookieInterceptor, key=" + aesKey.substring(0, 5) + "...");
        }
        else {
            logger.info("Wings conf WingsCookieInterceptor");
        }

        WingsCookieInterceptorImpl interceptor = new WingsCookieInterceptorImpl(aesKey);
        interceptor.setPrefix(slardarCookieProp.getPrefix());
        interceptor.setCoder(slardarCookieProp.getCoder());
        interceptor.addAlias(slardarCookieProp.getAlias());
        interceptor.addCodes(Nop, slardarCookieProp.getNop());
        interceptor.addCodes(B64, slardarCookieProp.getB64());
        interceptor.addCodes(Aes, slardarCookieProp.getAes());

        for (Map.Entry<Boolean, Set<String>> en : slardarCookieProp.getHttpOnly().entrySet()) {
            final Boolean k = en.getKey();
            for (String s : en.getValue()) {
                interceptor.addHttpOnly(s, k);
            }
        }

        for (Map.Entry<Boolean, Set<String>> en : slardarCookieProp.getSecure().entrySet()) {
            final Boolean k = en.getKey();
            for (String s : en.getValue()) {
                interceptor.addSecure(s, k);
            }
        }

        for (Map.Entry<String, Set<String>> en : slardarCookieProp.getDomain().entrySet()) {
            interceptor.addDomain(en.getKey(), en.getValue());
        }

        for (Map.Entry<String, Set<String>> en : slardarCookieProp.getPath().entrySet()) {
            interceptor.addPath(en.getKey(), en.getValue());
        }

        return interceptor;
    }

    @Bean
    public WingsCookieFilter wingsCookieFilter(WingsCookieInterceptor wingsCookieInterceptor) {
        logger.info("Wings conf WingsCookieFilter");
        WingsCookieFilter filter = new WingsCookieFilter(wingsCookieInterceptor);
        filter.setOrder(WingsServletConst.ORDER_FILTER_COOKIES);
        return filter;
    }
}
