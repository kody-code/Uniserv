package com.uniserv.app;

import cn.dev33.satoken.SaManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * Uniserv 启动类
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Slf4j
@SpringBootApplication
public class UniservApplication {
    static void main(String[] args) {
        SpringApplication.run(UniservApplication.class, args);
        log.info("启动成功，Sa-Token 配置如下：{}", SaManager.getConfig());
    }
}
