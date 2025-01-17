package pro.fessional.wings.slardar.security.pass;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import pro.fessional.mirana.bits.MdHelp;
import pro.fessional.mirana.code.RandCode;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author trydofor
 * @since 2021-02-25
 */
@Slf4j
class DefaultPasssaltEncoderTest {


    private PasswordEncoder bcrypt = new BCryptPasswordEncoder();
    private PasswordEncoder pbkdf2 = new Pbkdf2PasswordEncoder();
    private PasswordEncoder scrypt = new SCryptPasswordEncoder();
    private PasswordEncoder argon2 = new Argon2PasswordEncoder();
    private DefaultPasssaltEncoder sha256 = new DefaultPasssaltEncoder(MdHelp.sha256);

    /**
     * BCryptPasswordEncoder ms/100 =7745
     * $2a$10$3CcIgaVKkuXAYs.TTrzubeE8v0ztS7lBp3xuOjQk/nPdqD6aeIPSO
     * Pbkdf2PasswordEncoder ms/100 =41822
     * 7a3436424aedf17783960c4e1126c913269fc9a1e8dae3ac2ddb68d3a0d24e3a0c715e53a48b4711
     * SCryptPasswordEncoder ms/100 =6393
     * $e0801$JMWi0ppUTHoDrrtUr25XYK/3tWHF0eUWd5+MKgL68BNnoPWJJPwh28xNaHF505QFnJbbT7pKWwsu87Wq7HcZ8Q==$3rMHBkNIYtbrS+joy9GSDfBfydiCdcRyWWU+a4zmMVU=
     * Argon2PasswordEncoder ms/100 =1334
     * $argon2id$v=19$m=4096,t=3,p=1$N6bHhzVC9cZEWvhvy9n6pQ$3LgBEM5Hu/6KFjX1WXKxrxWTZRpL0ayZWuBFd5dp3IM
     */
    @Test
    void print() {
        final String str = RandCode.strong(100);
        time(bcrypt, str);
        time(pbkdf2, str);
        time(scrypt, str);
        time(argon2, str);
    }

    private void time(PasswordEncoder enc, String str) {
        long s = System.currentTimeMillis();
        int tm = 10;
        log.info("=====");
        for (int i = 0; i < tm; i++) {
            String pass = enc.encode(str);
            log.info(pass);
        }
        long e = System.currentTimeMillis();
        log.info(enc.getClass().getSimpleName() + " ms/" + tm + " =" + (e - s));
    }

    @Test
    void testSalt() {
        final String pass = RandCode.strong(5);
        final String salt = RandCode.strong(100);
        final String p1 = sha256.salt(pass, salt);
        final String p2 = sha256.salt(pass, salt);
        log.info(pass);
        log.info(salt);
        log.info(p1);
        assertEquals(p1, p2);
    }
}
