package com.kody.uniserv.app.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * Sa-Token 配置类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    /**
     * 注册 Sa-Token 拦截器，打开注解式鉴权功能
     * 配置全局的权限验证规则，包括登录验证和管理员权限验证
     *
     * @param registry 拦截器注册器，用于注册自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(_ -> {
            SaRouter.match("/**")
                    .notMatch("/api/auth/login", "/api/auth/register", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**",
                            "/v2/api-docs/**", "/webjars/**")
                    .check(_ -> StpUtil.checkLogin());

            SaRouter.match("/api/admin/**", _ -> StpUtil.checkRole("ADMIN"));
        })).addPathPatterns("/**");
    }

}
