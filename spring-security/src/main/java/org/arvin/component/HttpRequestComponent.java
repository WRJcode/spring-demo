package org.arvin.component;

import org.arvin.dto.SysUserDTO;
import org.arvin.exception.ApiException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class HttpRequestComponent {

    /**
     * 获取token
     */
    public String getToken(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            throw new RuntimeException("授权令牌为空");
        }
        return token;
    }

    /**
     * 获取用户信息
     */
    public SysUserDTO getAdminUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUserDTO userDetailsDTO = (SysUserDTO) authentication.getPrincipal();
        if (Objects.isNull(userDetailsDTO)){
            throw new ApiException("登录失效，请重新登录");
        }
        return userDetailsDTO;
    }

    /**
     * 获取用户ID
     */
    public Long getAdminUserId(){
        if (Objects.isNull(this.getAdminUserInfo().getSysUser())){
            throw new ApiException("登录失效，请重新登录");
        }
        return this.getAdminUserInfo().getSysUser().getId();
    }


}
