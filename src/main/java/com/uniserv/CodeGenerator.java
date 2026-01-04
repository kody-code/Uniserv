package com.uniserv;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    private static final String JAVA_OUTPUT_DIR = System.getProperty("user.dir") + "/src/main/java";
    private static final String XML_OUTPUT_DIR = System.getProperty("user.dir") + "/src/main/resources/mapper";

    public static void main(String[] args) {
        FastAutoGenerator.create(URL, USER, PASSWORD)
                .globalConfig(builder -> builder
                        .author("kody")
                        .outputDir(JAVA_OUTPUT_DIR)
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.uniserv")
                        .moduleName("auth")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                        .pathInfo(Collections.singletonMap(OutputFile.xml, XML_OUTPUT_DIR))
                )
                .strategyConfig(builder -> builder
                        .addInclude("users")
                        .entityBuilder()
                        .enableLombok()
                        .enableChainModel()
                        .enableTableFieldAnnotation()
                        .disableSerialVersionUID()
                        .controllerBuilder()
                        .enableHyphenStyle()
                        .enableRestStyle()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
