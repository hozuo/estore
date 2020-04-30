package top.ericson.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import top.ericson.pojo.Role;
import top.ericson.service.RoleService;
import top.ericson.service.UserService;
import top.ericson.vo.JsonResult;
import top.ericson.vo.PageObject;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.RoleInfo;

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

    /**
     * @author Ericson
     * @date 2020/04/29
     * @param roleId
     * @return JsonResult
     * @description 根据id查询用户名
     * TODO redis缓存
     */
    @GetMapping("/role/{id}/name")
    public JsonResult findroleNameById(@PathVariable("id") Integer roleId) {
        String roleName = roleService.findNameById(roleId);
        return JsonResult.success(roleName);
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
