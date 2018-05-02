package net.tradercat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.trianglex.common.config.WebConfig;
import org.trianglex.common.security.WebSecurityConfig;
import org.trianglex.common.security.XssRequestFilter;

@Configuration
@Import({
        WebConfig.class,
        WebSecurityConfig.class,
        XssRequestFilter.class,
        UserCentralProperties.class
})
public class TraderCatConfig {


}
