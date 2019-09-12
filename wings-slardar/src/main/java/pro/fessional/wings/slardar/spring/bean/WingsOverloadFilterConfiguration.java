package pro.fessional.wings.slardar.spring.bean;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pro.fessional.wings.slardar.http.TypedRequestUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * 自动计算单线程和全局请求数。
 * 收到TERM信号时，阻止所有请求。
 *
 * @author trydofor
 * @since 2019-07-23
 */

@Configuration
@ConditionalOnProperty(prefix = "spring.wings.filter.overload", name = "enabled", havingValue = "true")
@ConditionalOnClass(Filter.class)
public class WingsOverloadFilterConfiguration {

    private final Log logger = LogFactory.getLog(WingsOverloadFilterConfiguration.class);

    @Component
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @RequiredArgsConstructor
    public class SafelyShutdown implements ApplicationListener<ContextClosedEvent> {
        private final OverloadFilter overloadFilter;

        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            overloadFilter.requestCapacity.set(Integer.MIN_VALUE);
            AtomicInteger process = overloadFilter.requestProcess;
            logger.warn("shutting down, deny new request, current=" + process.get());
            while (process.get() > 0) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
            logger.warn("safely shutting down, no request in processing");
        }
    }

    @Bean
    @ConditionalOnMissingBean(FallBack.class)
    public FallBack overloadFallback(OverloadConfig config) {
        return (request, response) -> {
            try {
                if (response instanceof HttpServletResponse) {
                    HttpServletResponse res = (HttpServletResponse) response;
                    res.setStatus(config.getFallbackCode());
                }
                PrintWriter writer = response.getWriter();
                writer.println(config.getFallbackBody());
                writer.flush();
            } catch (IOException e) {
                // ignore
            }
        };
    }

    @Bean
    public FilterRegistrationBean overloadFilterRegister(OverloadFilter filter) {
        FilterRegistrationBean<OverloadFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(filter);
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public OverloadFilter overloadFilter(OverloadConfig config, FallBack fallBack) {
        return new OverloadFilter(fallBack, config);
    }

    @Bean
    @ConfigurationProperties("spring.wings.filter.overload")
    public OverloadConfig circuitBreakerFilterConfig() {
        return new OverloadConfig();
    }

    public class OverloadFilter implements Filter {

        private final AtomicInteger requestCapacity = new AtomicInteger(0);
        private final AtomicInteger requestProcess = new AtomicInteger(0);

        private final ConcurrentHashMap<String, Long> lastWarnSlow = new ConcurrentHashMap<>();
        private final AtomicLong lastInfoStat = new AtomicLong(0);

        private final int costStep = 20;
        private final AtomicLong[] responseCost; // 10s
        private final AtomicLong responseTotal = new AtomicLong(0);

        private final FallBack fallBack;
        private final OverloadConfig config;
        private final Cache<String, CalmDown> spiderCache;

        public OverloadFilter(FallBack fallBack, OverloadConfig config) {
            this.fallBack = fallBack;
            this.config = config;

            if (config.requestInterval <= 0 || config.requestCalmdown <= 0) {
                this.spiderCache = null;
            } else {
                int capacity = initCapacity(config);
                requestCapacity.set(capacity);

                this.spiderCache = Caffeine.newBuilder()
                                           .maximumSize(capacity)
                                           .expireAfterAccess(config.requestInterval * config.requestCalmdown * 2, MILLISECONDS)
                                           .build();
            }
            if (config.responseInfoStat <= 0) {
                responseCost = new AtomicLong[0];
            } else {
                responseCost = new AtomicLong[500];
            }
            for (int i = 0; i < responseCost.length; i++) {
                responseCost[i] = new AtomicLong(0);
            }
        }

        private int initCapacity(OverloadConfig config) {
            if (config.requestCapacity > 0) return config.requestCapacity;
            if (config.requestCapacity < 0) return Integer.MAX_VALUE;

            int cnt = Runtime.getRuntime().availableProcessors() * 300;
            return cnt < 2000 ? 2000 : cnt;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            if (requestProcess.get() > requestCapacity.get()) {
                fallBack.fallback(request, response);
                return;
            }

            if (!(request instanceof HttpServletRequest)) {
                chain.doFilter(request, response);
                return;
            }

            // 只能处理http的，目前的情况
            final long now = System.currentTimeMillis();
            final HttpServletRequest httpReq = (HttpServletRequest) request;
            final CalmDown calmDown = letCalmDown(httpReq);

            // 快请求，累积后清零
            if (calmDown != null) {
                final int rqs = calmDown.heardRequest.incrementAndGet(); // 等待处理的请求
                final boolean isCnt = rqs >= config.requestCalmdown;
                final boolean isFst = now - calmDown.firstRequest.get() < config.requestInterval;
                if (isCnt && isFst) {
                    fallBack.fallback(request, response);
                    if (logger.isWarnEnabled() && now > config.getLoggerInterval() + lastWarnSlow.getOrDefault(calmDown.ip, 0L)) {
                        logger.warn("wings-clam-request, now=" + rqs + ", ip=" + calmDown.ip + ", uri=" + httpReq.getRequestURI());
                        lastWarnSlow.put(calmDown.ip, now);
                    }
                    return; // 直接返回
                } else {
                    if (!isFst) calmDown.firstRequest.set(now);
                    if (isCnt) calmDown.heardRequest.addAndGet(-rqs);
                }
            }

            requestProcess.incrementAndGet();
            try {
                chain.doFilter(request, response);
                checkAndStats(httpReq, response, now, System.currentTimeMillis());
            } finally {
                requestProcess.decrementAndGet();
            }
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            // ignore
        }

        @Override
        public void destroy() {
            // ignore
        }

        private CalmDown letCalmDown(HttpServletRequest httpReq) {
            if (spiderCache == null) return null; // 不需要处理ip问题

            final String ip = TypedRequestUtil.getRemoteIp(httpReq, config.requestHeader);

            for (String p : config.requestPermit) {
                if (ip.startsWith(p)) {
                    return null; // 白名单，不需要处理。
                }
            }

            return spiderCache.get(ip, CalmDown::new);
        }

        //
        private void checkAndStats(HttpServletRequest request, ServletResponse response, long bgn, long end) {
            // 只处理成功的，其他的忽略。
            if (!(response instanceof HttpServletResponse)) return;

            HttpServletResponse res = (HttpServletResponse) response;
            if (res.getStatus() != 200) return;

            // 慢响应
            final long cost = end - bgn;
            final long warnSlow = config.responseWarnSlow;
            if (logger.isWarnEnabled() && warnSlow > 0 && cost > warnSlow) {
                String uri = request.getRequestURI();
                if (end > config.getLoggerInterval() + lastWarnSlow.getOrDefault(uri, 0L)) {
                    logger.warn("wings-slow-response, slow=" + warnSlow + ", cost=" + cost + ", uri=" + uri);
                    lastWarnSlow.put(uri, end);
                }
            }

            // 统计，已完成的请求，忽略并发误差。
            if (responseCost.length > 0) {
                int idx = (int) (cost % costStep);
                if (idx >= responseCost.length) {
                    responseCost[responseCost.length - 1].incrementAndGet();
                } else {
                    responseCost[idx].incrementAndGet();
                }
                long total = responseTotal.incrementAndGet();
                if (logger.isInfoEnabled()
                        && (config.responseInfoStat == 0 || total % config.responseInfoStat == 0)
                        && end - lastInfoStat.get() > config.responseInfoStat) {

                    long sum = 0;
                    int p99 = 0;
                    int p95 = 0;
                    int p90 = 0;
                    for (int i = 0; i < responseCost.length; i++) {
                        sum += responseCost[i].get();
                        long rate = sum * 100 / total;
                        if (rate >= 99 && p99 == 0) {
                            p99 = costStep * i;
                        }
                        if (rate >= 95 && p95 == 0) {
                            p95 = costStep * i;
                        }
                        if (rate >= 90 && p90 == 0) {
                            p90 = costStep * i;
                        }
                    }

                    logger.info("wings-snap-response "
                            + ", total-resp=" + total
                            + ", p99=" + p99
                            + ", p95=" + p95
                            + ", p90=" + p90
                            + ", process=" + requestProcess.get()
                            + ", capacity=" + requestCapacity.get());
                    lastInfoStat.set(end);
                }
            }
        }
    }


    @RequiredArgsConstructor
    private class CalmDown {
        private final String ip;
        private final AtomicLong firstRequest = new AtomicLong(0);
        private final AtomicInteger heardRequest = new AtomicInteger(0);
    }

    @Data
    public class OverloadConfig {
        private long loggerInterval = 3000;

        private int fallbackCode = 200;
        private String fallbackBody = "";

        private int requestCapacity = 0;
        private long requestInterval = -1;
        private int requestCalmdown = 50;
        private String[] requestPermit = {};
        private String[] requestHeader = {};

        private long responseWarnSlow = 4000;
        private long responseInfoStat = 1000;
    }

    public interface FallBack {
        void fallback(ServletRequest request, ServletResponse response);
    }
}
