package pro.fessional.wings.slardar.webmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pro.fessional.mirana.bits.Aes128;
import pro.fessional.mirana.bits.Base64;
import pro.fessional.wings.slardar.controller.TestCookieController.Ins;
import pro.fessional.wings.slardar.spring.prop.SlardarCookieProp;

import javax.servlet.http.Cookie;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pro.fessional.wings.slardar.httprest.OkHttpClientHelper.APPLICATION_JSON_VALUE;

/**
 * @author trydofor
 * @since 2020-06-03
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.wings.slardar.enabled.cookie=true",
                "wings.slardar.cookie.prefix=" + WingsCookieTest.PREFIX,
                "wings.slardar.cookie.coder=aes",
                "wings.slardar.cookie.alias[ck2]=" + WingsCookieTest.CK2OTH,
                "wings.slardar.cookie.nop=ck1,ck2",
                "wings.slardar.cookie.b64=b64",
                "wings.slardar.cookie.aes=aes",
        })
@AutoConfigureMockMvc
public class WingsCookieTest {

    public static final String PREFIX = "ti_";
    public static final String CK2OTH = "o_0";

    @Setter(onMethod_ = {@Autowired})
    private MockMvc mockMvc;

    @Setter(onMethod_ = {@Autowired})
    private SlardarCookieProp prop;

    @Setter(onMethod_ = {@Autowired})
    private ObjectMapper objectMapper;

    @Setter(onMethod_ = {@Value("http://127.0.0.1:${local.server.port}")})
    private String domain;

    @Setter(onMethod_ = {@Autowired})
    private OkHttpClient okHttpClient;

    @Test
    public void test1Cookie() throws Exception {
        mockMvcCookie("/test/cookie.json");
    }

    @Test
    public void test2Cookie() throws Exception {
        // 必须用 client，mock不执行
        httpClient("/test/cookie-forward.json");
        // mock mvc 并不真正执行 mapping方法
//        mockMvcCookie("/test/cookie-forward.json");
    }

    public void mockMvcCookie(String url) throws Exception {
        final Ins ins = new Ins();
        ins.setCk1("ck1-is-man");
        ins.setCk2("ck2-is-good");
        ins.setB64("base 64");
        ins.setAes("aes 128");
        ins.setOth("other");

        Aes128 aes128 = Aes128.of(prop.getAesKey());

        mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie(PREFIX + "ck1", ins.getCk1()),
                                new Cookie(PREFIX + CK2OTH, ins.getCk2()))
                        .content(objectMapper.writeValueAsString(ins))
        )
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(cookie().value(PREFIX + "ck1", ins.getCk1()))
               .andExpect(cookie().value(PREFIX + CK2OTH, ins.getCk2()))
               .andExpect(cookie().value(PREFIX + "b64", Base64.encode(ins.getB64())))
               .andExpect(cookie().value(PREFIX + "aes", aes128.encode64(ins.getAes())))
               .andExpect(cookie().value(PREFIX + "oth", aes128.encode64(ins.getOth())))
               .andExpect(content().string(ins.getCk1() + ins.getCk2()))
        ;
    }

    public void httpClient(String url) throws Exception {
        final Ins ins = new Ins();
        ins.setCk1("ck1-is-man");
        ins.setCk2("ck2-is-good");
        ins.setB64("base 64");
        ins.setAes("aes 128");
        ins.setOth("other");

        Aes128 aes128 = Aes128.of(prop.getAesKey());

        String cookieValue = PREFIX + "ck1=" + ins.getCk1() + "; " + PREFIX + CK2OTH + "=" + ins.getCk2();

        RequestBody body = RequestBody.create(objectMapper.writeValueAsString(ins), APPLICATION_JSON_VALUE);
        Request request = new Request.Builder()
                .url(domain + url)
                .addHeader("Cookie", cookieValue)
                .post(body)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            final ResponseBody resBody = response.body();
            assertEquals(200, response.code());
            assertNotNull(resBody);
            assertEquals(ins.getCk1() + ins.getCk2(), resBody.string());

            final Set<String> cookies = new HashSet<>(response.headers("Set-Cookie"));
            assertTrue(cookies.contains(PREFIX + "ck1=" + ins.getCk1()));
            assertTrue(cookies.contains(PREFIX + CK2OTH + "=" + ins.getCk2()));
            assertTrue(cookies.contains(PREFIX + "b64=" + Base64.encode(ins.getB64())));
            assertTrue(cookies.contains(PREFIX + "aes=" + aes128.encode64(ins.getAes())));
            assertTrue(cookies.contains(PREFIX + "oth=" + aes128.encode64(ins.getOth())));
        }
    }
}