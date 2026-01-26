package com.uniserv.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * <p>
 * 代码生成器
 * </p>
 *
 * @author kody
 * @since 2026-01-26
 */
public class CodeGenerator {
    private static final String JAVA_OUTPUT_DIR = "/src/main/java";

    private static final String URL = System.getenv("DB_URL");
    private static final String USERNAME = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

    static void main() {
        System.out.println("开始生成代码...");
        printDatasourceInfo();

        System.out.println("请输入位置：");
        Scanner input = new Scanner(System.in);
        String path = input.next();


        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> builder
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
                )
                .strategyConfig((scanner, builder) -> builder
                        .addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .entityBuilder()
                        .enableLombok()
                        .enableChainModel()
                        .idType(IdType.AUTO)
                        .enableTableFieldAnnotation()
                        .enableActiveRecord()
                        .disableSerialVersionUID()
                        .controllerBuilder()
                        .enableHyphenStyle()
                        .enableRestStyle()
                        .build()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

        System.out.println("代码生成完成...");
    }

    /**
     * 打印数据库连接信息
     */
    private static void printDatasourceInfo() {
        if (URL != null || USERNAME != null || PASSWORD != null) {
            System.out.println("数据库连接信息：");
            System.out.println("URL：" + URL);
            System.out.println("USERNAME：" + USERNAME);
            System.out.println("PASSWORD：" + PASSWORD);
        } else {
            System.err.println("请设置数据库连接信息：");
            System.exit(1);
        }
    }
}
