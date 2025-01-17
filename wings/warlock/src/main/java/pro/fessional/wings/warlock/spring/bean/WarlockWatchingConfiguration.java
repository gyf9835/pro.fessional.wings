package pro.fessional.wings.warlock.spring.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.ExecuteListenerProvider;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.fessional.wings.faceless.database.jooq.listener.SlowSqlListener;
import pro.fessional.wings.silencer.watch.WatchingAround;
import pro.fessional.wings.warlock.spring.prop.WarlockEnabledProp;
import pro.fessional.wings.warlock.spring.prop.WarlockWatchingProp;


/**
 * @author trydofor
 * @since 2019-12-01
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = WarlockEnabledProp.Key$watching, havingValue = "true")
public class WarlockWatchingConfiguration {

    private final static Log log = LogFactory.getLog(WarlockWatchingConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(WatchingAround.class)
    @ConditionalOnExpression("${" + WarlockWatchingProp.Key$serviceThreshold + ":-1} >=0")
    public WatchingAround watchingAround(WarlockWatchingProp prop) {
        final long ms = prop.getServiceThreshold();
        log.info("Warlock spring-bean watchingAround, threshold=" + ms);
        final WatchingAround bean = new WatchingAround();
        bean.setThresholdMillis(ms);
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean(name = "slowSqlListener")
    @ConditionalOnExpression("${" + WarlockWatchingProp.Key$jooqThreshold + ":-1} >=0")
    public ExecuteListenerProvider slowSqlListener(WarlockWatchingProp prop) {
        final long ms = prop.getJooqThreshold();
        log.info("Warlock spring-bean slowSqlListener, threshold=" + ms);
        final SlowSqlListener bean = new SlowSqlListener();
        bean.setThresholdMillis(ms);
        return new DefaultExecuteListenerProvider(bean);
    }
}
