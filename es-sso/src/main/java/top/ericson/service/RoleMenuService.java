package top.ericson.service;

import java.util.List;

import top.ericson.pojo.RoleMenuKey;

/**
 * @author Ericson
 * @class MenuService
 * @date 2020/05/01
 * @version 1.0
 * @description
 */
public interface RoleMenuService {

    Integer create(RoleMenuKey key);
    
    Integer delete(RoleMenuKey key);
    
    List<RoleMenuKey> findByRoleId(Integer roleId);
    
}
