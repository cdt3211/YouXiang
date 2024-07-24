package com.cupk.config;

import com.cupk.Interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**") // 指定拦截器的URL匹配模式
                .excludePathPatterns("/pleaseLogin","/register","/index","/index/**","/summary/**","/abstract/**","/logout","/loginAndRegister","/login","/home/**", "/css/**", "/js/**","/font/**"); // 指定不拦截的URL模式
    }
}