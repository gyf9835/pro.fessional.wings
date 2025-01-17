package pro.fessional.wings.slardar.servlet.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.function.Function;

/**
 * @author trydofor
 * @since 2021-10-08
 */
public class CookieResponseWrapper extends HttpServletResponseWrapper {

    private final Function<Cookie, Cookie> writer;

    public CookieResponseWrapper(HttpServletResponse response, Function<Cookie, Cookie> writer) {
        super(response);

        this.writer = writer;
    }

    @Override
    public void addCookie(Cookie cookie) {
        if (writer != null) {
            cookie = writer.apply(cookie);
        }
        if (cookie != null) {
            super.addCookie(cookie);
        }
    }
}
