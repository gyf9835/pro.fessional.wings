package pro.fessional.wings.slardar.spring.bean;

import com.hazelcast.core.HazelcastInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pro.fessional.wings.faceless.service.flakeid.FlakeIdService;
import pro.fessional.wings.slardar.event.EventPublishHelper;
import pro.fessional.wings.slardar.event.HazelcastSyncPublisher;
import pro.fessional.wings.slardar.service.flakeid.FlakeIdHazelcastImpl;

/**
 * @author trydofor
 * @since 2019-12-03
 */
@Configuration(proxyBeanMethods = false)
public class HazelcastOtherConfiguration {

    private static final Log log = LogFactory.getLog(HazelcastOtherConfiguration.class);

    @Bean
    @ConditionalOnClass(FlakeIdService.class)
    @Primary
    public FlakeIdService hazelcastFlakeId(HazelcastInstance instance) {
        log.info("SlardarHazelCaching spring-bean hazelcastFlakeId");
        return new FlakeIdHazelcastImpl(instance);
    }

    @Bean
    public CommandLineRunner hazelcastSyncPublisherRunner(HazelcastInstance instance, ApplicationEventPublisher publisher) {
        log.info("SlardarHazelCaching spring-runs hazelcastSyncPublisherRunner");
        return (arg) -> {
            HazelcastSyncPublisher global = new HazelcastSyncPublisher(instance, publisher);
            EventPublishHelper.setGlobalPublisher(global);
            log.info("SlardarHazelCaching conf HazelcastSyncPublisher as GlobalPublisher, uuid=" + global.getMessageListenerUuid());
        };
    }
}