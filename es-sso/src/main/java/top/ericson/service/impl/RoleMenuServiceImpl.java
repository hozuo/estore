 package top.ericson.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import top.ericson.mapper.RoleMenuMapper;
import top.ericson.pojo.RoleMenuKey;
import top.ericson.service.RoleMenuService;

/**
 * @author Ericson
 * @class RoleMenuServiceImpl
 * @date 2020/05/01 17:21
 * @version 1.0
 * @description 
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    RoleMenuMapper roleMenuMapper;
    
    /**
     * @author Ericson
     * @date 2020/05/01 17:21
     * @param key
     * @see top.ericson.service.RoleMenuService#create(top.ericson.pojo.RoleMenuKey)
     * @description 新建关联
     */
    @Override
    public Integer create(RoleMenuKey key) {
        return roleMenuMapper.insert(key);
    }

    /**
     * @author Ericson
     * @date 2020/05/01 17:21
     * @param id
     * @return
     * @see top.ericson.service.RoleMenuService#deleteById(java.lang.Integer)
     * @description 删除关联
     */
    @Override
    public Integer delete(RoleMenuKey key) {
        QueryWrapper<RoleMenuKey> wrapper = new QueryWrapper<RoleMenuKey>();
        wrapper.eq("role_id", key.getRoleId());
        wrapper.eq("menu_id", key.getMenuId());
        return roleMenuMapper.delete(wrapper);
    }

    /**
     * @author Ericson
     * @date 2020/05/01 17:27
     * @param roleId
     * @return
     * @see top.ericson.service.RoleMenuService#findByRoleId(java.lang.Integer)
     * @description 根据角色id查找关联
     */
    @Override
    public List<RoleMenuKey> findByRoleId(Integer roleId) {
        QueryWrapper<RoleMenuKey> wrapper = new QueryWrapper<RoleMenuKey>();
        wrapper.eq("role_id", roleId);
        return roleMenuMapper.selectList(wrapper);
    }

}
