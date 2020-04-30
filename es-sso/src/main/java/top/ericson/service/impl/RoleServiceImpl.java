package top.ericson.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
import top.ericson.mapper.RoleMapper;
import top.ericson.pojo.Role;
import top.ericson.service.RoleService;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.RoleInfo;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    HttpServletRequest request;

    /**
     * @author Ericson
     * @date 2020/04/29 20:26
     * @param roleInfo
     * @see top.ericson.service.RoleService#createRole(top.ericson.vo.info.RoleInfo)
     * @description 新建
     */
    @Override
    public void create(RoleInfo roleInfo) {
        Role role = roleInfo.BuildPojo();
        Integer userId = (Integer)request.getAttribute("userId");
        log.debug("roleId:{}", userId);
        Date now = new Date();
        role.setCreateTime(now)
            .setCreateUser(userId)
            .setUpdateTime(now)
            .setUpdateUser(userId);
        roleMapper.insert(role);
    }

    /**
     * @author Ericson
     * @date 2020/04/29
     * @param roleId
     * @return
     * @see top.ericson.service.RoleService#findRoleNameById(java.lang.Integer)
     * @description 
     */
    @Override
    public String findNameById(Integer id) {
        return roleMapper.selectRoleNameById(id);
    }

    /**
     * @author Ericson
     * @date 2020/04/29
     * @param idSet
     * @see top.ericson.service.RoleService#findRolesNameById(java.util.Set)
     * @description 
     */
    @Override
    public Map<Integer, String> findNamesById(Set<Integer> idSet) {
        log.debug("idSet:{}", idSet);
        List<Role> roleList = roleMapper.selectRolesNameById(idSet);
        HashMap<Integer, String> rolesNameMap = new HashMap<Integer, String>();
        for (Role role : roleList) {
            rolesNameMap.put(role.getRoleId(), role.getRolename());
        }
        return rolesNameMap;
    }

    @Override
    public IPage<Role> findPage(PageQuery pageQuery) {

        /*开启分页查询*/
        Page<Role> page = new Page<>(pageQuery.getPageCurrent(), pageQuery.getPageSize());

        /*条件构造器*/
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();

        // 名称查询
        if (pageQuery.getName() != null) {
            queryWrapper.like("name", pageQuery.getName());
        }
        // 排序
        if (pageQuery.getOrderBy() != null && !"".equals(pageQuery.getOrderBy())) {
            queryWrapper.orderBy(true, pageQuery.getIsASC(), pageQuery.getOrderBy());
        }

        IPage<Role> iPage = roleMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    /**
     * @author Ericson
     * @date 2020/04/29 15:00
     * @param id
     * @return
     * @see top.ericson.service.RoleService#deleteById(java.lang.Integer)
     * @description 
     */
    @Override
    public Integer deleteById(Integer id) {
        return roleMapper.deleteById(id);
    }

    /**
     * @author Ericson
     * @date 2020/04/29 16:01
     * @param id
     * @param roleInfo
     * @return
     * @see top.ericson.service.RoleService#updateById(java.lang.Integer, top.ericson.vo.info.RoleInfo)
     * @description 
     */
    @Override
    public Integer updateById(RoleInfo roleInfo) {
        Role role = roleInfo.BuildPojo();
        role.setRoleId(roleInfo.getRoleId());
        Integer userId = (Integer)request.getAttribute("userId");
        role.setUpdateTime(new Date())
            .setUpdateUser(userId);
        return roleMapper.updateById(role);
    }

}
