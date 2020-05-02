package top.ericson.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import top.ericson.service.MenuService;
import top.ericson.service.RoleMenuService;
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
    private MenuService menuService;

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
     * @param pageQuery
     * @return
     * @description 分页查询
     */
    @GetMapping("/menus")
    public JsonResult findPage(PageQuery pageQuery, String type) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        if ("tree".equals(type)) {
            
            // 联合查询menu
            List<Menu> menuList = menuService.findAll();
            // 构建info的map集合
            Map<Integer, RoleMenuInfo> infoMap = new HashMap<Integer, RoleMenuInfo>();
            for (Menu menu : menuList) {
                infoMap.put(menu.getMenuId(), new RoleMenuInfo(menu.getMenuId(), menu.getParentId(), menu.getMenuName(),
                    menu.getType(), new ArrayList<>()));
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
