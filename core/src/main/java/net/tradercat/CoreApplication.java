package net.tradercat;

import net.tradercat.config.TraderCatConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Import;

@SpringCloudApplication
@Import(TraderCatConfig.class)
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}
