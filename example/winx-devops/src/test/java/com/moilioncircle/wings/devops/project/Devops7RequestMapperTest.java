package com.moilioncircle.wings.devops.project;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import pro.fessional.wings.slardar.webmvc.RequestMappingHelper;

import java.util.List;

/**
 * ⑥ 使用wings的flywave，生成trigger和跟踪表
 *
 * @author trydofor
 * @since 2019-12-26
 */

@SpringBootTest
@Disabled("手动执行，版本更新时处理")
@Slf4j
public class Devops7RequestMapperTest {

    @Setter(onMethod_ = {@Autowired})
    private ApplicationContext context;

    @Test
    public void infoAllMapping() {
        List<RequestMappingHelper.Info> infos = RequestMappingHelper.infoAllMapping(context);
        for (RequestMappingHelper.Info info : infos) {
            log.info(info.toJson());
        }
    }
}
