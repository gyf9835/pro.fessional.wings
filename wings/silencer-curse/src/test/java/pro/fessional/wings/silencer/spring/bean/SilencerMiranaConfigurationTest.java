package pro.fessional.wings.silencer.spring.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.fessional.mirana.code.Crc8Long;
import pro.fessional.mirana.code.LeapCode;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-10-05
 */

@SpringBootTest(properties = {"debug = true"})
public class SilencerMiranaConfigurationTest {

    private Crc8Long crc8Long;
    private LeapCode leapCode;

    @Autowired
    public void setCrc8Long(Crc8Long crc8Long) {
        this.crc8Long = crc8Long;
    }

    @Autowired
    public void setLeapCode(LeapCode leapCode) {
        this.leapCode = leapCode;
    }

    @Test
    public void testCode() {
        long number = 1979L;
        long encode = crc8Long.encode(number);
        long decode = crc8Long.decode(encode);
        assertEquals(number, decode);

        String s = leapCode.encode26(number);
        long decode1 = leapCode.decode(s);
        assertEquals(number, decode1);
    }
}
