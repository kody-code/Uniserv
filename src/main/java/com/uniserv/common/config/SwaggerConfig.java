package com.uniserv.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Swagger 配置
 * </p>
 *
 * @author kody
 * @since 2026-01-04
 */
@Configuration
public class SwaggerConfig {
    
    private static final String TOKEN_NAME = "uniserv-token";

    @Bean
    public OpenAPI openApi() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(TOKEN_NAME);
        Info info = new Info()
                .title("UniServ 接口文档")
                .version("0.0.1")
                .description("UniServ 系统接口文档，包含用户、权限等核心接口")
                .contact(new Contact().name("kody").email("zhaojiew.wang@gmail.com"));
        return new OpenAPI()
                .openapi("3.0.3")
                .info(info)
                .components(new Components().addSecuritySchemes(TOKEN_NAME, securityScheme))
                .addSecurityItem(new SecurityRequirement().addList(TOKEN_NAME));
    }
}
