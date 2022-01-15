package pro.fessional.wings.warlock.controller.auth;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.data.R;
import pro.fessional.wings.slardar.security.WingsAuthPageHandler;
import pro.fessional.wings.slardar.security.WingsAuthTypeParser;
import pro.fessional.wings.slardar.servlet.ContentTypeHelper;
import pro.fessional.wings.slardar.servlet.resolver.WingsRemoteResolver;
import pro.fessional.wings.warlock.security.justauth.AuthStateBuilder;
import pro.fessional.wings.warlock.security.session.NonceTokenSessionHelper;
import pro.fessional.wings.warlock.spring.prop.WarlockEnabledProp;
import pro.fessional.wings.warlock.spring.prop.WarlockSecurityProp;
import pro.fessional.wings.warlock.spring.prop.WarlockUrlmapProp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author trydofor
 * @since 2021-02-16
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = WarlockEnabledProp.Key$controllerAuth, havingValue = "true")
public class LoginPageController {

    private final WingsAuthPageHandler wingsAuthPageHandler;
    private final WingsAuthTypeParser wingsAuthTypeParser;
    private final WingsRemoteResolver wingsRemoteResolver;

    @Setter(onMethod_ = {@Autowired(required = false)})
    private HttpSessionIdResolver httpSessionIdResolver;

    @SuppressWarnings("MVCPathVariableInspection")
    @ApiOperation(value = "集成登录默认页，默认返回支持的type类表", notes =
            "# Usage \n"
            + "列出支持的登录方式。具体恢复内容，以根据extName和request.ContentType推测的MediaType确定\n"
            + "比如`html`和`json`扩展名，默认实现中，结果都以json形式返回\n"
            + "## Params \n"
            + "* @param extName - 路径参数，扩展名，如html,json\n"
            + "## Returns \n"
            + "* @return {401} 当鉴权失败，有系统forward时 \n"
            + "* @return {200} 直接访问或redirect时 \n"
            + "")
    @RequestMapping(value = "${" + WarlockUrlmapProp.Key$authLoginList + "}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<?> loginList(@PathVariable("extName") String extName,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        final MediaType mt = ContentTypeHelper.mediaTypeByUri(extName);
        log.info("default login-page media-type={}", mt);
        return wingsAuthPageHandler.response(Null.Enm, mt, request, response);
    }

    @SuppressWarnings("MVCPathVariableInspection")
    @ApiOperation(value = "具体验证登录默认页，根据content-type及extName规则做相应的处理", notes =
            "# Usage \n"
            + "一般用于构造访问入口，如Oauth2登录的第三方路径和参数；获取反扒登录的验证码\n"
            + "需要注意state是数组，是spring支持的http协议的参数数组，如`a=1&a=2&a=3`\n"
            + "``` bash \n"
            + "curl -X POST 'http://localhost:8084/auth/github/login-page.json' \\\n"
            + "--data 'state=/order-list&state=http://localhost:8080&state=&host=localhost:8080'\n"
            + "curl -X GET  \"http://localhost:8084/auth/github/login-page.json\\\n"
            + "?host=localhost:8080&state=/order-list&state=http://localhost%3A8080&state=\"\n"
            + "```\n"
            + "## Params \n"
            + "* @param authType - 验证类型，系统配置项，可由【集成登录】查看，比如email,github \n"
            + "* @param {string[]} state - 构造Oauth2的state，MessageFormat格式，state[0]作为Format的key,state整体是Format的参数; \n"
            + "* @param host - 构造Oauth2的重定向host，以减少跨域 \n"
            + "## Returns \n"
            + "* @return {401} 当鉴权失败，有系统forward时 \n"
            + "* @return {200} 直接访问或redirect时 \n"
            + "")
    @RequestMapping(value = "${" + WarlockUrlmapProp.Key$authLoginPage + "}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<?> LoginPage(@PathVariable("authType") String authType,
                                       @PathVariable("extName") String extName,
                                       @RequestParam(value = AuthStateBuilder.ParamState, required = false) List<String> state,
                                       @RequestParam(value = "host", required = false) String host,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        final Enum<?> em = wingsAuthTypeParser.parse(authType);
        final MediaType mt = ContentTypeHelper.mediaTypeByUri(extName, MediaType.APPLICATION_JSON);
        log.info("{} login-page media-type={}, state={}, host={}", authType, mt, state, host);
        return wingsAuthPageHandler.response(em, mt, request, response);
    }

    @SuppressWarnings("UastIncorrectHttpHeaderInspection")
    @ApiOperation(value = "验证一次性token是否有效", notes =
            "# Usage \n"
            + "Oauth2使用state作为token，要求和发行client具有相同ip，agent等header信息\n"
            + "验证成功后，在header中，可同样获取login时的session和cookie\n"
            + "## Params \n"
            + "* @param token - Oauth2使用state作为token\n"
            + "## Returns \n"
            + "* @return {401} 无|过期|失败 \n"
            + "* @return {200 | Result(false, message='authing')} 验证进行中 \n"
            + "* @return {200 | Result(true, data=sessionId)} 验证成功 \n"
            + "")
    @PostMapping(value = "${" + WarlockUrlmapProp.Key$authNonceCheck + "}")
    public ResponseEntity<R<?>> nonceCheck(@RequestHeader("token") String token, HttpServletRequest request, HttpServletResponse response) {
        final String sid = NonceTokenSessionHelper.authNonce(token, wingsRemoteResolver.resolveRemoteKey(request));
        if (sid == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(R.ng());
        }
        else {
            final R<?> r;
            if (sid.isEmpty()) {
                r = R.ng("authing");
            }
            else {
                r = R.okData(sid);
                if (httpSessionIdResolver != null) {
                    httpSessionIdResolver.setSessionId(request, response, sid);
                }
            }

            return ResponseEntity.ok(r);
        }
    }

    @ApiOperation(value = "登出接口，有filter处理，仅做文档", notes =
            "# Usage \n"
            + "默认失效Session，参考wings.warlock.security.logout-url\n"
            + "## Params \n"
            + "* @param token - Oauth2使用state作为token\n"
            + "## Returns \n"
            + "* @return {200} 任何时候 \n"
            + "")
    @PostMapping(value = "${" + WarlockSecurityProp.Key$logoutUrl + "}")
    public String logout() {
        return "handler by filter, never here";
    }


    @SuppressWarnings("MVCPathVariableInspection")
    @ApiOperation(value = "登录接口，有filter处理，仅做文档", notes =
            "# Usage \n"
            + "根据类型自动处理，参考 wings.warlock.security.login-url\n"
            + "username和password可变，参考 参考 wings.warlock.security.username-para\n"
            + "登录成功后，可在header中获得token和session\n"
            + "## Params \n"
            + "* @param authType - 验证类型，系统配置项，可由【集成登录】查看，比如email,github \n"
            + "* @param username - Oauth2使用state作为token\n"
            + "* @param password - Oauth2使用state作为token\n"
            + "## Returns \n"
            + "* @return {200} 登录成功 \n"
            + "")
    @PostMapping(value = "${" + WarlockSecurityProp.Key$loginUrl + "}")
    public String login(@PathVariable("authType") String authType,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password) {
        log.info("authType={}, username={}, password={}", authType, username, password);
        return "handler by filter, never here";
    }
}
