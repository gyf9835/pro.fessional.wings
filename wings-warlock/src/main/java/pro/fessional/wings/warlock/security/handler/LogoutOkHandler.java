package pro.fessional.wings.warlock.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import pro.fessional.wings.slardar.servlet.response.ResponseHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * invalid session
 *
 * @author trydofor
 * @since 2021-02-17
 */
@Slf4j
@RequiredArgsConstructor
public class LogoutOkHandler implements LogoutSuccessHandler {

    private final String body;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final HttpSession session = request.getSession(false);
        if (session != null) {
            log.info("logout success, session-id={}", session.getId());
            session.invalidate();
        } else {
            log.info("logout success, but no-session");
        }
        ResponseHelper.writeBodyUtf8(response, body);
    }
}
