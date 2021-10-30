package pro.fessional.wings.slardar.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
 *
 * @author trydofor
 * @since 2021-07-05
 */
@SpringBootTest
public class ExternalConfigTest {

    @Value("${random.value}")
    private String randomValue;
    @Value("${random.uuid}")
    private String randomUuid;

    @Test
    public void testRandom() {
        System.out.println("random-value=" + randomValue);
        System.out.println("random-uuid=" + randomUuid);
    }
}
