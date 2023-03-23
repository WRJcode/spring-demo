package org.arvin.config;

import org.arvin.filter.AdminUsernamePasswordAuthenticationFilter;
import org.arvin.filter.MobileAuthenticationFilter;
import org.arvin.handler.MobileAuthenticationFailureHandler;
import org.arvin.handler.MobileAuthenticationSuccessHandler;
import org.arvin.provider.MobileAuthenticationProvider;
import org.arvin.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityMobileConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    MobileAuthenticationSuccessHandler mobileAuthenticationSuccessHandler;

    @Autowired
    MobileAuthenticationFailureHandler mobileAuthenticationFailureHandler;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();
        mobileAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(mobileAuthenticationSuccessHandler);
        mobileAuthenticationFilter.setAuthenticationFailureHandler(mobileAuthenticationFailureHandler);

        //new 一个mobileAuthenticationProvider
        MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider();
        mobileAuthenticationProvider.setUserDetailsService(userDetailsService);
        //注入过滤器
        http.authenticationProvider(mobileAuthenticationProvider)
            .addFilterAfter(mobileAuthenticationFilter, AdminUsernamePasswordAuthenticationFilter.class);
    }

}
