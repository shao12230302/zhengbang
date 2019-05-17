package com.zb.byb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(EasConfig.class)
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private OpenIdInterceptor openIdInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        registry.addInterceptor(openIdInterceptor);
    }
}