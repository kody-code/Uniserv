package com.kody.uniserv.app;

import cn.dev33.satoken.SaManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Slf4j
@ComponentScan("com.kody.uniserv")
@SpringBootApplication(scanBasePackages = "com.kody.uniserv")
public class UniservApplication {
    static void main(String[] args) {
        SpringApplication.run(UniservApplication.class, args);
        log.info("启动成功，Sa-Token 配置如下：{}", SaManager.getConfig());
    }
}
