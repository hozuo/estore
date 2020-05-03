package top.ericson.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.Menu;
import top.ericson.pojo.Role;
import top.ericson.pojo.RoleMenuKey;
import top.ericson.service.MenuService;
import top.ericson.service.RoleMenuService;
import top.ericson.service.RoleService;
import top.ericson.service.UserService;
import top.ericson.vo.JsonResult;
import top.ericson.vo.PageObject;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.RoleInfo;
import top.ericson.vo.info.RoleMenuInfo;

/**
 * @author Ericson
 * @class roleController
 * @date 2020/04/09 15:55
 * @version 1.0
 * @description 用户管理认证
 */
@Slf4j
@RestController
public class RoleController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    /**
     * @author Ericson
     * @date 2020/04/29
     * @param roleId
     * @return JsonResult
     * @description 根据id查询用户名
     * TODO redis缓存
     */
    @GetMapping("/role/{id}/name")
    public JsonResult findRoleNameById(@PathVariable("id") Integer roleId) {
        String roleName = roleService.findNameById(roleId);
        return JsonResult.success(roleName);
    }

    /**
     * @author Ericson
     * @date 2020/05/01 17:13
     * @param roleId
     * @return
     * @description 查询一个角色对应的权限列表
     */
    @GetMapping("/role/{id}/menus")
    public JsonResult findRoleMenusById(@PathVariable("id") Integer roleId) {
        // 查询关联表,获得menuId列表
        List<RoleMenuKey> keyList = roleMenuService.findByRoleId(roleId);
        log.debug("keyList:{}", keyList);
        // 定义联合查询集合
        Set<Integer> menuIdSet = new HashSet<>();
        for (RoleMenuKey key : keyList) {
            menuIdSet.add(key.getMenuId());
        }
        // 联合查询menu
        List<Menu> menuList = menuService.findByIds(menuIdSet);
        // 构建info的map集合
        Map<Integer, RoleMenuInfo> infoMap = new HashMap<Integer, RoleMenuInfo>();
        for (Menu menu : menuList) {
            infoMap.put(menu.getMenuId(), new RoleMenuInfo(menu));
        }
        log.debug("infoMap:{}", infoMap);
        // 遍历三级叶子结点
        for (Entry<Integer, RoleMenuInfo> entry : infoMap.entrySet()) {
            if (entry.getValue()
                .getType() == 3) {
                infoMap.get(entry.getValue()
                    .getParentId())
                    .add(entry.getValue());
            }
        }
        // 遍历二级结点
        for (Entry<Integer, RoleMenuInfo> entry : infoMap.entrySet()) {
            if (entry.getValue()
                .getType() == 2) {
                infoMap.get(entry.getValue()
                    .getParentId())
                    .add(entry.getValue());
            }
        }
        List<RoleMenuInfo> infoList = new ArrayList<>();
        // 遍历根结点
        for (Entry<Integer, RoleMenuInfo> entry : infoMap.entrySet()) {
            if (entry.getValue()
                .getType() == 1) {
                infoList.add(entry.getValue());
            }
        }

        return JsonResult.success(infoList);
    }

    /**
     * @author Ericson
     * @date 2020/05/01 23:57
     * @param roleId
     * @param menuId
     * @return
     * @description 删除用户权限
     */
    @DeleteMapping("/role/{roleId}/menus/{menuId}")
    public JsonResult deleRoleMenuById(@PathVariable Integer roleId, @PathVariable Integer menuId) {
        // 根据id查询menu
        Menu menu = menuService.findById(menuId);
        Integer type = menu.getType();
        // 删除该节点的记录
        Integer deleteNum = roleMenuService.delete(new RoleMenuKey(roleId, menuId));
        if (type == 3) {
            if (deleteNum == 1) {
                return JsonResult.success("删除用户权限成功");
            } else {
                return JsonResult.msg("删除失败");
            }
        }
        if (type == 2) {
            List<Menu> menuListType2_3 = menuService.findByParentId(menu.getMenuId());
            log.debug("menuListType2_3:{}", menuListType2_3);
            if (menuListType2_3 != null) {
                // 遍历删除叶子结点
                for (Menu menu2_3 : menuListType2_3) {
                    roleMenuService.delete(new RoleMenuKey(roleId, menu2_3.getMenuId()));
                }
            }
            return JsonResult.success("删除用户权限成功");
        }
        if (type == 1) {
            // 查询根节点的二级节点
            List<Menu> menuListType1_2 = menuService.findByParentId(menu.getMenuId());
            if (menuListType1_2 != null) {
                List<Menu> menuListType1_3;
                // 遍历二级节点
                for (Menu menu1_2 : menuListType1_2) {
                    // 查询当前二级节点的叶子结点
                    menuListType1_3 = menuService.findByParentId(menu1_2.getMenuId());
                    if (menuListType1_3 != null) {
                        // 删除全部叶子结点
                        for (Menu menu1_3 : menuListType1_3) {
                            roleMenuService.delete(new RoleMenuKey(roleId, menu1_3.getMenuId()));
                        }
                    }
                    // 删除当前二级节点
                    roleMenuService.delete(new RoleMenuKey(roleId, menu1_2.getMenuId()));
                }
            }
        }
        return JsonResult.success("删除用户权限成功");
    }

    /**
     * @author Ericson
     * @date 2020/05/02 21:43
     * @param roleId
     * @param menuIdSet
     * @return
     * @description 更新用户的权限
     */
    @PutMapping("/role/{roleId}/menus")
    public JsonResult deleRoleMenuById(@PathVariable Integer roleId, @RequestParam("menuId") Set<Integer> menuIdSet) {
        // 删除该用户的所有权限
        roleMenuService.deleteByRoleId(roleId);
        for (Integer menuId : menuIdSet) {
            roleMenuService.create(new RoleMenuKey(roleId, menuId));
        }
        return JsonResult.success("更新用户权限成功");
    }

    /**
     * @author Ericson
     * @date 2020/04/29
     * @param roleInfo
     * @return
     * @description 新建
     */
    @PostMapping("/role")
    public JsonResult createrole(RoleInfo roleInfo) {
        log.debug("roleTemplate:{}", roleInfo);
        /*写入用户*/
        roleService.create(roleInfo);
        return JsonResult.success();
    }

    /**
     * @author Ericson
     * @date 2020/04/29 15:48
     * @param id
     * @return
     * @description 删除
     */
    @DeleteMapping("/role/{id}")
    public JsonResult deleteById(@PathVariable(value = "id") Integer id) {
        Integer deleteNum = roleService.deleteById(id);
        if (deleteNum == 1) {
            return JsonResult.success("删除一条记录");
        } else {
            return JsonResult.fail();
        }
    }

    /**
     * @author Ericson
     * @date 2020/04/29 16:00
     * @param id
     * @param roleInfo
     * @return
     * @description 更新
     */
    @PutMapping("/role/{id}")
    public JsonResult updateById(@PathVariable(value = "id") Integer id, RoleInfo roleInfo) {
        roleInfo.setRoleId(id);
        Integer updateNum = roleService.updateById(roleInfo);
        if (updateNum == 1) {
            return JsonResult.success("更新一条记录");
        } else {
            return JsonResult.fail();
        }
    }

    /*复数资源*/
    /**
     * @author Ericson
     * @date 2020/04/15 17:19
     * @param idSet
     * @return
     * @description 查询很多role的name
     */
    @GetMapping("/roles/search/name")
    public JsonResult findrolesNameById(@RequestParam(value = "id") Set<Integer> idSet) {
        log.debug("set:{}", idSet);
        Map<Integer, String> itemsNameMap = roleService.findNamesById(idSet);
        return JsonResult.success(itemsNameMap);
    }

    /**
     * @author Ericson
     * @date 2020/04/29 18:32
     * @param pageQuery
     * @return
     * @description 分页查询
     */
    @GetMapping("/roles")
    public JsonResult findPage(PageQuery pageQuery) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        // 判断orderBy是否合法
        pageQuery.setOrderBy(RoleInfo.orderByCheak(pageQuery.getOrderBy()));
        // 分页查询
        IPage<Role> iPage = roleService.findPage(pageQuery);
        if (iPage == null) {
            return JsonResult.fail();
        }
        // 获得list
        List<Role> roleList = iPage.getRecords();
        if (roleList == null) {
            return JsonResult.fail();
        }
        // 构建联合查询集合
        Set<Integer> userIdSet = new HashSet<>();
        for (Role r : roleList) {
            userIdSet.add(r.getUpdateUser());
            userIdSet.add(r.getCreateUser());
        }
        // 联合查询
        Map<Integer, String> usernameMap = userService.findNamesById(userIdSet);
        // 构造infolist
        List<RoleInfo> roleInfoList = RoleInfo.buildInfoList(roleList, usernameMap);

        PageObject<RoleInfo> pageObject = new PageObject<RoleInfo>(iPage, roleInfoList);

        return JsonResult.success(pageObject);
    }

}
