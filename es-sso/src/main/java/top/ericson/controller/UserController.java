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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.util.JSONPObject;

import lombok.extern.slf4j.Slf4j;
import top.ericson.exception.ServiceException;
import top.ericson.mapper.UserMapper;
import top.ericson.pojo.User;
import top.ericson.service.UserService;
import top.ericson.util.JwtUtilPrivate;
import top.ericson.vo.JsonResult;
import top.ericson.vo.PageObject;
import top.ericson.vo.PageQuery;
import top.ericson.vo.ResultCode;
import top.ericson.vo.info.UserInfo;

/**
 * @author Ericson
 * @class UserController
 * @date 2020/04/09 15:55
 * @version 1.0
 * @description 用户管理认证
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @author Ericson
     * @date 2020/04/15 15:12
     * @param userId
     * @return JsonResult
     * @description 根据id查询用户名
     * TODO redis缓存
     */
    @GetMapping("/user/{id}/name")
    public JsonResult findUserNameById(@PathVariable("id") Integer userId) {
        String userName = userService.findNameById(userId);
        return JsonResult.success(userName);
    }

    /**
     * @author Ericson
     * @date 2020/04/15 15:14
     * @param username
     * @param password
     * @param isRememberMe
     * @return
     * @description 登录
     */
    @RequestMapping("/user/login")
    @ResponseBody
    public JsonResult doLogin(String username, String password, boolean isRememberMe) {
        /*TODO 验证用户名密码是否正确*/
        User user = userService.findByName(username);
        if (user == null) {
            return JsonResult.msg("用户名或密码错误");
        }
        /*创建token*/
        String jwt = JwtUtilPrivate.buildJwt(user.getUserId(), username);
        return JsonResult.success(jwt);
    }

    /**
     * @author Ericson
     * @date 2020/04/09 15:56
     * @param param
     * @param type
     * @param callback
     * @return
     * @description
     *  这是JSONP跨域请求,使用JSONPObject封装数据
     *  Url地址:http://sso.ericson.top/user/check/{param}/{type}
     *  参数说明:
     *      1. param 用户校验的参数
     *      2. type  校验的字段 1 username 2 phone 3 email
     *  返回值: SysResult 
     */
    @GetMapping("/user/cheak/{param}/{type}")
    public JSONPObject checkUser(@PathVariable String param, @PathVariable String type, String callback) {
        boolean flag = false;
        if ("username".equals(type)) {
            flag = userService.checkUser(param, 1);
        } else if ("phone".equals(type)) {
            flag = userService.checkUser(param, 2);
        } else if ("email".equals(type)) {
            flag = userService.checkUser(param, 3);
        } else {
            return new JSONPObject(callback, JsonResult.build(ResultCode.PARAMS_ERROR));
        }
        log.debug("flag:{}", flag);
        return new JSONPObject(callback, JsonResult.success(flag));
    }

    /**
     * @author Ericson
     * @date 2020/04/15 15:08
     * @param userInfo
     * @return
     * @description 注册
     */
    @PostMapping("/user")
    public JsonResult createUser(UserInfo userInfo) {
        log.debug("userTemplate:{}", userInfo);
        /*数据校验*/
        boolean flag1 = userService.checkUser(userInfo.getUsername(), 1);
        if (!flag1) {
            ServiceException e = new ServiceException("前端校验失效,传入非法用户名");
            return JsonResult.exce(e);
        }
        boolean flag2 = userService.checkUser(userInfo.getPhone(), 2);
        if (!flag2) {
            ServiceException e = new ServiceException("前端校验失效,传入非法电话号码");
            return JsonResult.exce(e);
        }
        boolean flag3 = userService.checkUser(userInfo.getEmail(), 3);
        if (!flag3) {
            ServiceException e = new ServiceException("前端校验失效,传入非法email");
            return JsonResult.exce(e);
        }
        /*写入用户*/
        userService.createUser(userInfo);
        return JsonResult.success();
    }

    /**
     * @author Ericson
     * @date 2020/04/29 15:48
     * @param id
     * @return
     * @description 删除用户
     */
    @DeleteMapping("/user/{id}")
    public JsonResult deleteById(@PathVariable(value = "id") Integer id) {
        Integer deleteNum = userService.deleteById(id);
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
     * @param userInfo
     * @return
     * @description 更新
     */
    @PutMapping("/user/{id}")
    public JsonResult updateById(@PathVariable(value = "id") Integer id, UserInfo userInfo) {
        userInfo.setUserId(id);
        Integer updateNum = userService.updateById(userInfo);
        if (updateNum == 1) {
            return JsonResult.success("更新一条记录");
        } else {
            return JsonResult.fail();
        }
    }

    @PutMapping("/user/{id}/valid/{valid}")
    public JsonResult updateValidById(@PathVariable Integer id, @PathVariable Integer valid) {
        User user = new User();
        user.setUserId(id)
            .setValid(valid);
        Integer updateNum = userService.updateValidById(user);
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
     * @description 查询很多user的name
     */
    @GetMapping("/users/search/name")
    public JsonResult findUsersNameById(@RequestParam(value = "id") Set<Integer> idSet) {
        log.debug("set:{}", idSet);
        Map<Integer, String> itemsNameMap = userService.findNamesById(idSet);
        return JsonResult.success(itemsNameMap);
    }

    @GetMapping("/users")
    public JsonResult findPage(PageQuery pageQuery) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        // 判断orderBy是否合法
        pageQuery.setOrderBy(UserInfo.orderByCheak(pageQuery.getOrderBy()));
        // 分页查询
        IPage<User> iPage = userService.findPage(pageQuery);
        if (iPage == null) {
            return JsonResult.fail();
        }
        // 获得list
        List<User> userList = iPage.getRecords();
        if (userList == null) {
            return JsonResult.fail();
        }
        // 构建联合查询集合
        Set<Integer> idSet = new HashSet<>();
        for (User s : userList) {
            idSet.add(s.getUpdateUser());
            idSet.add(s.getCreateUser());
        }
        // 联合查询用户名
        Map<Integer, String> usernameMap = userService.findNamesById(idSet);
        // 构造infolist
        List<UserInfo> userInfoList = UserInfo.buildInfoList(userList, usernameMap);

        PageObject<UserInfo> pageObject = new PageObject<UserInfo>(iPage, userInfoList);

        return JsonResult.success(pageObject);
    }

}
