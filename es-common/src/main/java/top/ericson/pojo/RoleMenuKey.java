package top.ericson.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author Ericson
 * @class RoleMenuKey
 * @date 2020/05/01 17:18
 * @version 1.0
 * @description 角色权限关联表
 */
@Data
@TableName("sys_role_menu")
public class RoleMenuKey implements Serializable{

    private static final long serialVersionUID = -4068155997065804527L;

    @TableField
    private Integer roleId;

    @TableField
    private Integer menuId;

}