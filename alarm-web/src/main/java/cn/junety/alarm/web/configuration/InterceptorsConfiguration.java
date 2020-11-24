package cn.junety.alarm.web.configuration;

import cn.junety.alarm.web.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by caijt on 2017/4/2.
 */
@Configuration
public class InterceptorsConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    UserLoginInterceptor userLoginInterceptor() {
        return new UserLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/monitor/**");
        super.addInterceptors(registry);
    }
}
