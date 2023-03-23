package org.arvin.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arvin.pojo.SysMenu;
import org.arvin.pojo.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class SysUserDTO implements UserDetails {

    private SysUser sysUser;

    private List<SysMenu> sysMenu;



    //不进行序列化
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public SysUserDTO(SysUser sysUser,List<SysMenu> menus){
        this.sysUser = sysUser;
        this.sysMenu = menus;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (!CollectionUtils.isEmpty(authorities)){
            return authorities;
        }
        for (SysMenu menu:sysMenu){
            String perms = menu.getPerms();
            if (StringUtils.hasLength(perms)){
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(menu.getPerms());
                authorities.add(simpleGrantedAuthority);
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.sysUser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
