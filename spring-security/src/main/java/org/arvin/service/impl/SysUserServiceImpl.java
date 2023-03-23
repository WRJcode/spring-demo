package org.arvin.service.impl;

import org.arvin.pojo.SysUser;
import org.arvin.mapper.SysUserMapper;
import org.arvin.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author arvin
 * @since 2023-03-22
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
