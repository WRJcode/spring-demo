package org.arvin.config;

import org.arvin.handler.AuthenticationEntryPointIHandler;
import org.arvin.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Resource
    SpringSecurityAdminConfig springSecurityAdminConfig;

    @Resource
    SpringSecurityMobileConfig springSecurityMobileConfig;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    AuthenticationEntryPointIHandler authenticationEntryPointIHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //禁用它，因为前后端分离不需要
                .csrf(AbstractHttpConfigurer::disable)
                //禁用session
                .sessionManagement(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auhtor-> auhtor
                        //这里配置的接口不需要验证
                        .antMatchers("/admin/code","/admin/login","/mobile/code").permitAll()
                        .antMatchers("/user/sysUser/list").hasAuthority("sysUser/list")
                        //其它接口都需要经过验证
                        .anyRequest().authenticated()

                );
        http.apply(springSecurityAdminConfig);
        //注入新的AuthenticationManager
        http.authenticationManager(authenticationManager(http));

        //认证异常处理器
        http.exceptionHandling(ex->ex.authenticationEntryPoint(authenticationEntryPointIHandler));

        http.apply(springSecurityMobileConfig);

        return http.build();
    }

    /**
     *密码加密规则
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     *构造一个AuthenticationManager，使用自定义的userDetailsService和passwordEncoder
     */
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
        return authenticationManager;
    }

}

