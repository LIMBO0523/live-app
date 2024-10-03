package org.live.web.starter.config;

import org.live.web.starter.interceptor.UserInfoInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 15:28
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public UserInfoInterceptor UserInfoInterceptor() {
        return new UserInfoInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(UserInfoInterceptor()).addPathPatterns("/**").excludePathPatterns("/error");
    }

}
