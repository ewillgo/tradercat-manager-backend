package net.tradercat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.trianglex.usercentral.api.core.UasInterceptor;

@Configuration
@Import(UasInterceptor.class)
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    private UasInterceptor uasInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }
}
