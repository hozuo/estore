package top.ericson.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.Menu;
import top.ericson.pojo.RoleMenuKey;
import top.ericson.pojo.User;
import top.ericson.service.MenuService;
import top.ericson.service.RoleMenuService;
import top.ericson.service.UserService;
import top.ericson.vo.JsonResult;
import top.ericson.vo.PageObject;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.RoleMenuInfo;

/**
 * @author Ericson
 * @class menuController
 * @date 2020/04/30
 * @version 1.0
 * @description 菜单也是权限
 */
@Slf4j
@RestController
public class MenuController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private UserService userService;

    /**
     * @author Ericson
     * @date 2020/04/30
     * @param menu
     * @return
     * @description 新建
     */
    @PostMapping("/menu")
    public JsonResult createmenu(Menu menu) {
        log.debug("menuTemplate:{}", menu);
        /*写入用户*/
        menuService.create(menu);
        return JsonResult.success();
    }

    /**
     * @author Ericson
     * @date 2020/04/30
     * @param id
     * @return
     * @description 删除
     */
    @DeleteMapping("/menu/{id}")
    public JsonResult deleteById(@PathVariable(value = "id") Integer id) {
        Integer deleteNum = menuService.deleteById(id);
        if (deleteNum == 1) {
            return JsonResult.success("删除一条记录");
        } else {
            return JsonResult.fail();
        }
    }

    /**
     * @author Ericson
     * @date 2020/04/30
     * @param id
     * @param menu
     * @return
     * @description 更新
     */
    @PutMapping("/menu/{id}")
    public JsonResult updateById(@PathVariable(value = "id") Integer id, Menu menu) {
        menu.setMenuId(id);
        Integer updateNum = menuService.updateById(menu);
        if (updateNum == 1) {
            return JsonResult.success("更新一条记录");
        } else {
            return JsonResult.fail();
        }
    }

    /*复数资源*/
    /**
     * @author Ericson
     * @date 2020/04/30
     * @param type 
     * 默认list,返回分页查询的list结果,可以用于展示权限列表
     * tree,返回树形遍历的结果,用于展示树形结构的选框
     * menu,返回当前用户左侧的菜单列表,为最高两级的树形结构
     * @return
     * @description 分页查询
     */
    @GetMapping("/menus")
    public JsonResult findPage(PageQuery pageQuery, String type) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        // 查询树形结构
        if ("tree".equals(type)) {
            // 查询全部menu
            List<Menu> menuList = menuService.findAll();
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
        } else if ("menu".equals(type)) {
            /*查询当前角色的左侧菜单*/
            // 获得当前用户id
            Integer userId = (Integer)request.getAttribute("userId");
            // 查询当前用户
            User user = userService.findById(userId);
            // 查询关联表,获得用户的menuId列表
            List<RoleMenuKey> keyList = roleMenuService.findByRoleId(user.getRoleId());
            log.debug("keyList:{}", keyList);
            // 定义menu联合查询集合
            Set<Integer> menuIdSet = new HashSet<>();
            for (RoleMenuKey key : keyList) {
                menuIdSet.add(key.getMenuId());
            }
            // 联合查询menu
            List<Menu> menuList = menuService.findByIds(menuIdSet);
            // 构建info返回的map集合
            Map<Integer, RoleMenuInfo> infoMap = new HashMap<Integer, RoleMenuInfo>();
            for (Menu menu : menuList) {
                infoMap.put(menu.getMenuId(), new RoleMenuInfo(menu));
            }
            log.debug("infoMap:{}", infoMap);

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
        } else {

            // 分页查询
            IPage<Menu> iPage = menuService.findPage(pageQuery);
            if (iPage == null) {
                return JsonResult.fail();
            }
            // 获得list
            List<Menu> menuList = iPage.getRecords();
            if (menuList == null) {
                return JsonResult.fail();
            }

            PageObject<Menu> pageObject = new PageObject<Menu>(iPage, menuList);

            return JsonResult.success(pageObject);
        }
    }

}
