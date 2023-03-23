package org.arvin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.protobuf.ServiceException;
import org.arvin.dto.SysUserDTO;
import org.arvin.exception.ApiException;
import org.arvin.mapper.SysUserMapper;
import org.arvin.pojo.SysMenu;
import org.arvin.pojo.SysUser;
import org.arvin.service.BaseUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements BaseUserDetailsService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUserDTO loadUserByPhone(String phone) throws UsernameNotFoundException {

        //用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getPhone,phone);
        List<SysUser> sysUsers = sysUserMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(sysUsers)){
            throw new ApiException("该用户不存在");
        }
        return new SysUserDTO(sysUsers.get(0),null);
    }

    @Override
    public SysUserDTO loadUserByUsername(String username) throws UsernameNotFoundException {

        //通过用户名查询用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName, username);
        List<SysUser> sysUsers = sysUserMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(sysUsers)){
            throw new RuntimeException("该用户不存在");
        }

        //获取权限信息
        List<SysMenu> userHasMenu = sysUserMapper.getUserHasMenu(sysUsers.get(0).getId());

        return new SysUserDTO(sysUsers.get(0),userHasMenu);

    }
}
