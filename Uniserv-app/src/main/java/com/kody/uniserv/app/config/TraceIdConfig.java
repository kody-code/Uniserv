package com.kody.uniserv.app.config;

import com.kody.uniserv.common.handler.TraceIdInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * TraceId 配置类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Configuration
public class TraceIdConfig implements WebMvcConfigurer {

    /**
     * 注册 TraceIdInterceptor 拦截器
     *
     * @return TraceIdInterceptor 拦截器
     */
    @Bean
    public TraceIdInterceptor traceIdInterceptor() {
        return new TraceIdInterceptor();
    }

    /**
     * 添加拦截器
     *
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册TraceId拦截器，拦截所有请求
        registry.addInterceptor(traceIdInterceptor())
                .addPathPatterns("/**")
                // 可根据实际情况排除不需要拦截的路径，如swagger、健康检查等
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**", "/actuator/**");
    }
}