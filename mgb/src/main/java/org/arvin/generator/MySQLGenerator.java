package org.arvin.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;


public class MySQLGenerator extends BaseGenerator{

    /**
     * 配置数据源
     */

    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://localhost:3306/spring?useUnicode=true&useSSL=false&characterEncoding=utf8",
            "root", "")
            .schema("arvin")
            .build();

    public static void main(String[] args){
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.global(globalConfig().build());
        generator.packageInfo(packageConfig().build());
        generator.injection(injectionConfig().build());
        generator.template(template().build());
        generator.execute();
    }
}
