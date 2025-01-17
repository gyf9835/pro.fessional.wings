package pro.fessional.wings.slardar.spring.bean;

import okhttp3.OkHttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import pro.fessional.wings.slardar.spring.prop.SlardarEnabledProp;

/**
 * @author trydofor
 * @link https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#boot-features-resttemplate-customization
 * @see RestTemplateAutoConfiguration#RestTemplateAutoConfiguration()
 * @since 2020-05-22
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(OkHttpClient.class)
@ConditionalOnProperty(name = SlardarEnabledProp.Key$okhttp, havingValue = "true")
public class SlardarOkhttpWebConfiguration {

    private static final Log log = LogFactory.getLog(SlardarOkhttpWebConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(RestTemplateBuilder.class)
    public RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer, OkHttpClient client) {
        log.info("SlardarWebmvc spring-bean restTemplateBuilder");
        final RestTemplateBuilder builder = configurer.configure(new RestTemplateBuilder());
        return builder.requestFactory(() -> new OkHttp3ClientHttpRequestFactory(client));
    }

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate okRestTemplate(RestTemplateBuilder builder) {
        log.info("SlardarWebmvc spring-bean okRestTemplate");
        return builder.build();
    }

}
