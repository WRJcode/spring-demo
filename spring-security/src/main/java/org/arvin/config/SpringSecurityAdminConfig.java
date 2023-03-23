package org.arvin.config;

import org.arvin.filter.AdminAuthenticationTokenFilter;
import org.arvin.filter.AdminUsernamePasswordAuthenticationFilter;
import org.arvin.handler.AdminAuthenticationFailureHandler;
import org.arvin.handler.AdminAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAdminConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    AdminAuthenticationSuccessHandler adminAuthenticationSuccessHandler;

    @Autowired
    AdminAuthenticationFailureHandler adminAuthenticationFailureHandler;

    @Autowired
    AdminAuthenticationTokenFilter adminAuthenticationTokenFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        AdminUsernamePasswordAuthenticationFilter adminUsernamePasswordAuthenticationFilter = new AdminUsernamePasswordAuthenticationFilter();
        adminUsernamePasswordAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        adminUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(adminAuthenticationSuccessHandler);//传入我们自定义的成功的处理器
        adminUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(adminAuthenticationFailureHandler);
        //注入过滤器,addFilterAt替换UsernamePasswordAuthenticationFilter
        http.addFilterAt(adminUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(adminAuthenticationTokenFilter,AdminUsernamePasswordAuthenticationFilter.class);
    }
}

