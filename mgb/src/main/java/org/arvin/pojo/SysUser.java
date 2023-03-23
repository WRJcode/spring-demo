package org.arvin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author arvin
 * @since 2023-03-22
 */
@TableName("sys_user")
@Getter
@Setter
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号状态（0正常 1停用）
     */
    private String status;

    @Override
    public String toString() {
        return "SysUser{" +
        "id = " + id +
        ", userName = " + userName +
        ", nickName = " + nickName +
        ", phone = " + phone +
        ", password = " + password +
        ", status = " + status +
        "}";
    }
}
