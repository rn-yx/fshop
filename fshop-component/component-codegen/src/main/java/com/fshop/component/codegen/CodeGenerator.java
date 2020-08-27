/*
 * Copyright [2020] [rannuo]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fshop.component.codegen;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.*;

/**
 * Description: Mybatis Plus代码生成器
 *
 * @author 然诺
 * @date 2020/2/22
 */
public class CodeGenerator {

    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://192.168.171.128:3306/seckill_user?autoReconnect=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
    private static final String USERNAME = "root";
    private static final String PASS = "rn1234";
    private static final String AUTHOR = "rannuo1010@gmail.com";
    private static final String PROJECT_PATH = "D:\\JavaDev\\IDEA_J\\fshop\\fshop-user\\fshop-user-provider";
    private static final String PROJECT_CODING_PATH = "\\src\\main\\java\\com\\fshop\\user";
    private static final String PARENT_PACKAGE = "com.fshop.user";
    private static final String SUPER_CONTROLLER_CLASS = "com.fshop.component.core.mvc.BaseController";
    private static final String GLOBAL_RET_CLASS = "com.fshop.component.core.result.GlobalRet";

    public static void main(String[] args) {
        execute();
    }

    /**
     * 主执行流程
     */
    private static void execute() {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(PROJECT_PATH + "\\src\\main\\java");
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setSwagger2(true);
        gc.setBaseColumnList(true);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName(DRIVER_CLASS_NAME);
        dsc.setUrl(URL);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASS);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent(PARENT_PACKAGE);

        // 自定义映射配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>(3);
                map.put("GlobalRet", GLOBAL_RET_CLASS);
                map.put("domainPackage", pc.getParent() + ".model");
                this.setMap(map);
            }
        };

        // 自定义输出配置, 自定义配置会被优先输出
        List<FileOutConfig> focList = new ArrayList<>();
        // 配置模板引擎
        final String mapperTemplatePath = "/templates/mapper.xml.vm";
        final String entityDtoTemplatePath = "/templates/entityDTO.java.vm";
        final String entityVoTemplatePath = "/templates/entityVO.java.vm";
        // mapper.xml输出文件
        focList.add(new FileOutConfig(mapperTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名，如果Entity设置了前后缀、此处注意xml的名称会跟着发生变化
                return PROJECT_PATH + "\\src\\main\\resources\\mappers\\" + pc.getModuleName() + "\\" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        // entityDTO输出文件
        focList.add(new FileOutConfig(entityDtoTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return PROJECT_PATH + PROJECT_CODING_PATH + "\\" + pc.getModuleName() + "\\model\\dto\\" + tableInfo.getEntityName() + "DTO" + StringPool.DOT_JAVA;
            }
        });
        // entityVO输出文件
        focList.add(new FileOutConfig(entityVoTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return PROJECT_PATH + PROJECT_CODING_PATH + "\\" + pc.getModuleName() + "\\model\\vo\\" + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA;
            }
        });
        cfg.setFileOutConfigList(focList);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        // 配置自定义输出模板。指定自定义模板路径，注意不要带上.ftl或.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("/templates/entity.java");
        templateConfig.setMapper("/templates/mapper.java");
        templateConfig.setService("/templates/service.java");
        templateConfig.setServiceImpl("/templates/serviceImpl.java");
        templateConfig.setController("/templates/controller.java");

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        //strategy.setSuperEntityClass("x.x.x.BaseEntity");
        //strategy.setSuperEntityColumns("id");
        strategy.setSuperControllerClass(SUPER_CONTROLLER_CLASS);
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setInclude(scanner("表名(多表以英文逗号分割)").split(","));
        strategy.setTablePrefix(pc.getModuleName() + "_");

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(gc);
        mpg.setDataSource(dsc);
        mpg.setPackageInfo(pc);
        mpg.setCfg(cfg);
        mpg.setTemplate(templateConfig);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }

    /**
     * 读取控制台内容
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}
