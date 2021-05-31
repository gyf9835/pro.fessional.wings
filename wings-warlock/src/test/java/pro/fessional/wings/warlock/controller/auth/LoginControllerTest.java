package pro.fessional.wings.warlock.controller.auth;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.fessional.mirana.code.RandCode;
import pro.fessional.wings.warlock.event.auth.WarlockNonceSendEvent;
import pro.fessional.wings.warlock.service.auth.WarlockAuthType;

/**
 * @author trydofor
 * @since 2021-02-27
 */
@RestController
@Slf4j
public class LoginControllerTest {

    @Setter(onMethod_ = {@Autowired})
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/auth/console-nonce.json")
    public String loginPageDefault(@RequestParam("username") String user) {
        Enum<?> authType = WarlockAuthType.USERNAME;
        String pass = RandCode.human(16);
        long expire = System.currentTimeMillis() + 300_000;
        WarlockNonceSendEvent event = new WarlockNonceSendEvent();
        event.setAuthType(authType);
        event.setExpired(expire);
        event.setUsername(user);
        event.setNonce(pass);
        applicationEventPublisher.publishEvent(event);
        log.warn("root username-nonce is " + pass);
        return "see console log warn";
    }
}
