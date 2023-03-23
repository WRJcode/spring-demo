package org.arvin.controller;

import org.arvin.api.CommonResult;
import org.arvin.pojo.SysUser;
import org.arvin.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author arvin
 * @since 2023-03-22
 */
@RestController
@RequestMapping("/user/sysUser")
public class SysUserController {

    @Autowired
    ISysUserService sysUserService;

    @GetMapping("/{id}")
    public CommonResult<SysUser> getUserById(@PathVariable("id")int id){
        return CommonResult.success(sysUserService.getById(id));
    }

    @GetMapping("/index")
    @PreAuthorize("hasAuthority('sysUser/list')")
    public CommonResult<Void> index(){
        return CommonResult.success(null,"成功访问到用户列表");
    }

}
