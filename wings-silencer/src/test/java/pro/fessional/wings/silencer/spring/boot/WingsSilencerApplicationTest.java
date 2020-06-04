package pro.fessional.wings.silencer.spring.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * @author trydofor
 * @since 2019-06-25
 */

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"debug = true"})
public class WingsSilencerApplicationTest {

    private MessageSource messageSource;
    private String module;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Value("${wings.test.module}")
    public void setModule(String module) {
        this.module = module;
    }

    @Test
    public void cnEn() {
        assertEquals("沉默术士", module);
        String cn = messageSource.getMessage("base.not-empty", new Object[]{"姓名"}, Locale.CHINA);
        String en = messageSource.getMessage("base.not-empty", new Object[]{"name"}, Locale.US);
        assertEquals("姓名 不能为空", cn);
        assertEquals("name can not be empty", en);
    }
}