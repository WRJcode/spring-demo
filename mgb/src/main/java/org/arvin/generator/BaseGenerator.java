package org.arvin.generator;

import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import java.util.Collections;

public class BaseGenerator {

    /**
     * 策略配置
     */

    protected static StrategyConfig.Builder strategyConfig(){
        return new StrategyConfig.Builder()
                ;
    }

    /**
     * 全局配置
     */
    protected static GlobalConfig.Builder globalConfig(){
        return new GlobalConfig.Builder()
                .outputDir("E:\\others project\\spring-demo\\mgb\\src\\main\\java")
                .author("arvin")
                .dateType(DateType.TIME_PACK)
                .commentDate("yyyy-MM-dd");
    }

    /**
     * 包配置
     */
    protected static PackageConfig.Builder packageConfig(){
        return new PackageConfig.Builder()
                .parent("org.arvin")
                .pathInfo(Collections.singletonMap(OutputFile.xml,"E:\\others project\\spring-demo\\mgb\\src\\main\\resources\\mapper"))
                .entity("pojo")
                .service("service")
                .serviceImpl("service.impl")
                .controller("controller");
    }

    /**
     * 模板配置
     */
    protected static TemplateConfig.Builder template() {
        return new TemplateConfig.Builder();
    }

    /**
     * 注入配置
     */
    protected static InjectionConfig.Builder injectionConfig(){
        // 测试自定义输出文件之前注入操作，该操作再执行生成代码前 debug 查看
        return new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
        });
    }

}
