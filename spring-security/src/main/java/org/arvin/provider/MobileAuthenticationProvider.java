package org.arvin.provider;

import org.arvin.dto.SysUserDTO;
import org.arvin.service.impl.UserDetailsServiceImpl;
import org.arvin.token.MobileAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Objects;

public class MobileAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken =
                (MobileAuthenticationToken) authentication;

        //通过手机号从数据库中查询用户信息
        SysUserDTO sysUserDTO = this.userDetailsService.loadUserByPhone(
                mobileAuthenticationToken.getPrincipal().toString());

        if (Objects.isNull(sysUserDTO)){
            throw new BadCredentialsException("手机登录失败");
        }

        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(sysUserDTO,
                sysUserDTO.getAuthorities());
        authenticationToken.setDetails(authenticationToken.getCredentials());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsServiceImpl getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
