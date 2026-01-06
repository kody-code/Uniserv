package com.uniserv.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * <p>
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
public class CodeGenerator {

    private static final String JAVA_OUTPUT_DIR = "/src/main/java";
    private static final String XML_OUTPUT_DIR = "/src/main/resources/mapper";

    private static final String URL = System.getenv("DB_URL");
    private static final String USERNAME = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");


    public static void main(String[] args) {
        System.out.println("开始生成代码...");

        System.out.println("请输入位置：");
        Scanner input = new Scanner(System.in);
        String path = input.next();

        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig((scanner, builder) -> builder
                        .author("kody")
                        .outputDir(path + JAVA_OUTPUT_DIR)
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig((scanner, builder) -> builder
                        .parent("com.uniserv." + scanner.apply("请输入包名："))
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                        .pathInfo(Collections.singletonMap(OutputFile.xml, path + XML_OUTPUT_DIR))
                )
                .strategyConfig((scanner, builder) -> builder
                        .addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
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

        System.out.println("代码生成完成...");
    }

    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
