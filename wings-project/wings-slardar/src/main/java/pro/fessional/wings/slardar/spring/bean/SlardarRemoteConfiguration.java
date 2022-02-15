package pro.fessional.wings.slardar.spring.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.fessional.wings.slardar.servlet.resolver.WingsRemoteResolver;
import pro.fessional.wings.slardar.spring.prop.SlardarEnabledProp;
import pro.fessional.wings.slardar.spring.prop.SlardarRemoteProp;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author trydofor
 * @since 2019-06-29
 */
@Configuration
@ConditionalOnProperty(name = SlardarEnabledProp.Key$remote, havingValue = "true")
public class SlardarRemoteConfiguration {

    private final Log logger = LogFactory.getLog(SlardarRemoteConfiguration.class);

    @Bean
    public WingsRemoteResolver wingsTerminalResolver(SlardarRemoteProp conf) {
        logger.info("Wings conf WingsRemoteResolver");
        final WingsRemoteResolver resolver = new WingsRemoteResolver();
        final Collection<String> inp = new HashSet<>(conf.getInnerIp().values());
        inp.removeIf(it -> it == null || it.isEmpty());
        resolver.addInnerIp(inp);

        final Collection<String> ahp = new HashSet<>(conf.getAgentHeader().values());
        ahp.removeIf(it -> it == null || it.isEmpty());
        resolver.addAgentHeader(ahp);

        final Collection<String> ihp = new HashSet<>(conf.getIpHeader().values());
        ihp.removeIf(it -> it == null || it.isEmpty());
        resolver.addIpHeader(ihp);
        return resolver;
    }
}
