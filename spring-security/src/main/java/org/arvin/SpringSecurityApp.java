package org.arvin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("org.arvin.mapper")
public class SpringSecurityApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(SpringSecurityApp.class,args);
    }
}
