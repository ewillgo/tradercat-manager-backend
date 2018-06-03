package net.tradercat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.trianglex.common.config.WebConfig;
import org.trianglex.common.security.XssRequestFilter;
import org.trianglex.common.security.cors.WebSecurityCorsConfig;

@Configuration
@Import({
        WebConfig.class,
        WebSecurityCorsConfig.class,
        XssRequestFilter.class
})
public class TraderCatConfig {

}
