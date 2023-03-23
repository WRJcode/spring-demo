package org.arvin.mapper;

import org.arvin.pojo.SysMenu;
import org.arvin.pojo.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author arvin
 * @since 2023-03-22
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysMenu> getUserHasMenu(Long id);

}
