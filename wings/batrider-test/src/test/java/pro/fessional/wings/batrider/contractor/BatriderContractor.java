package pro.fessional.wings.batrider.contractor;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pro.fessional.wings.batrider.contract.HelloContract;

/**
 * Mvc风格，自定义SchemaId，basePath不带SchemaId
 *
 * @author trydofor
 * @since 2022-08-04
 */
@RestSchema(schemaId = "batrider-hello")
@RequestMapping(path = "/")
public class BatriderContractor implements HelloContract {

    @RequestMapping(path = "/batrider-hello/say-hello", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    @Override
    public String sayHello(@RequestParam(name = "name") String name) {
        return "Batrider: Hello " + name;
    }
}
