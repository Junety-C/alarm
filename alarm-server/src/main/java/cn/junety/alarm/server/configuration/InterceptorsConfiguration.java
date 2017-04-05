package cn.junety.alarm.server.configuration;

import cn.junety.alarm.server.interceptor.MonitorInterceptor;
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
    MonitorInterceptor monitorInterceptor() {
        return new MonitorInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(monitorInterceptor()).addPathPatterns("/v1/alarm");
        super.addInterceptors(registry);
    }
}
