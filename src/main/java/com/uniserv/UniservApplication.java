package com.uniserv;

import cn.dev33.satoken.SaManager;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan("com.uniserv.**.mapper")
public class UniservApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniservApplication.class, args);
        log.info("启动成功，Sa-Token 配置如下：{}", SaManager.getConfig());
    }

}
