package top.ericson.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;

import lombok.extern.slf4j.Slf4j;
import top.ericson.exception.ServiceException;
import top.ericson.pojo.User;
import top.ericson.service.UserService;
import top.ericson.util.JwtUtilPrivate;
import top.ericson.vo.JsonResult;
import top.ericson.vo.ResultCode;
import top.ericson.vo.template.UserTemplate;

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
        String userName = userService.findUserNameById(userId);
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
     * @param userTemplate
     * @return
     * @description 注册
     */
    @PostMapping("/user/register")
    public JsonResult createUser(UserTemplate userTemplate) {
        log.debug("userTemplate:{}", userTemplate);
        /*数据校验*/
        boolean flag1 = userService.checkUser(userTemplate.getUsername(), 1);
        if (!flag1) {
            ServiceException e = new ServiceException("前端校验失效,传入非法用户名");
            return JsonResult.exce(e);
        }
        boolean flag2 = userService.checkUser(userTemplate.getPhone(), 2);
        if (!flag2) {
            ServiceException e = new ServiceException("前端校验失效,传入非法电话号码");
            return JsonResult.exce(e);
        }
        boolean flag3 = userService.checkUser(userTemplate.getEmail(), 3);
        if (!flag3) {
            ServiceException e = new ServiceException("前端校验失效,传入非法email");
            return JsonResult.exce(e);
        }
        /*写入用户*/
        userService.createUser(userTemplate.getUsername(), userTemplate.getPassword(), userTemplate.getEmail(),
            userTemplate.getPhone(), userTemplate.getInvitation());
        return JsonResult.success();
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
        Map<Integer, String> itemsNameMap = userService.findUsersNameById(idSet);
        return JsonResult.success(itemsNameMap);
    }

}
