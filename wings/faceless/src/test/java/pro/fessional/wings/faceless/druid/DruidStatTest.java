package pro.fessional.wings.faceless.druid;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author trydofor
 * @since 2021-01-08
 */
@SpringBootTest(properties = {"debug = true"})
@Slf4j
public class DruidStatTest {
    @Test
    public void druidStat() {
        final List<Map<String, Object>> stat = DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
        log.info("druidStat={}", stat);
    }
}
