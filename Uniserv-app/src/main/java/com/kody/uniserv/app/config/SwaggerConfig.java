package com.kody.uniserv.app.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Swagger 配置类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Configuration
public class SwaggerConfig {
    /**
     * token 名称
     */
    private static final String TOKEN_NAME = "uniserv-token";

    /**
     * 全局 OpenAPI 配置（包含文档基本信息、全局认证配置）
     */
    @Bean
    public OpenAPI openApi() {
        // 配置 token 头部认证
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(TOKEN_NAME);

        // 文档基本信息
        Info info = new Info()
                .title("UniServ 接口文档")
                .version("0.0.1")
                .description("UniServ 系统接口文档，包含用户、权限等核心接口")
                .contact(new Contact().name("kody").email("zhaojiew.wang@gmail.com"));

        // 构建全局 OpenAPI 配置（所有分组共享此基础配置）
        return new OpenAPI()
                .openapi("3.0.3")
                .info(info)
                .components(new Components().addSecuritySchemes(TOKEN_NAME, securityScheme))
                .addSecurityItem(new SecurityRequirement().addList(TOKEN_NAME));
    }

    /**
     * 认证模块 API 分组
     * 扫描路径：com.uniserv.auth.controller，接口路径：/api/auth/**
     */
    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("认证模块")
                .packagesToScan("com.kody.uniserv.app.controller.auth")
                .pathsToMatch("/api/auth/**")
                .build();
    }

    /**
     * 密码模块 API 分组
     * 扫描路径：com.uniserv.cipher.controller，接口路径：/api/cipher/**
     */
    @Bean
    public GroupedOpenApi cipherApi() {
        return GroupedOpenApi.builder()
                .group("密码管理模块")
                .packagesToScan("com.uniserv.cipher.controller")
                .pathsToMatch("/api/cipher/**")
                .build();
    }

    /**
     * 账本模块 API 分组
     * 扫描路径：com.uniserv.ledger.controller，接口路径：/api/ledger/**
     */
    @Bean
    public GroupedOpenApi ledgerApi() {
        return GroupedOpenApi.builder()
                .group("账本模块")
                .packagesToScan("com.uniserv.ledger.controller")
                .pathsToMatch("/api/ledger/**")
                .build();
    }
}
