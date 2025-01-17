package pro.fessional.wings.slardar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * @author trydofor
 * @since 2019-07-20
 */
@SpringBootApplication
public class WingsSlardarApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WingsSlardarApplication.class);
        app.setApplicationStartup(new BufferingApplicationStartup(4096));
        app.run(args);
    }
}
