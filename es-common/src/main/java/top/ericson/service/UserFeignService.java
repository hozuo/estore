package top.ericson.service;

import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import top.ericson.vo.JsonResult;

/**
 * @author Ericson
 * @class UserFeignService
 * @date 2020/04/15 15:15
 * @version 1.0
 * @description 用户feginservice
 */
@FeignClient(name = "sso-service")
public interface UserFeignService {
    /**
     * @author Ericson
     * @date 2020/04/15 15:12
     * @param userId
     * @return JsonResult
     * @description 根据id查询用户名
     */
    @GetMapping("/user/{id}/name")
    public JsonResult findUserNameById(@PathVariable("id") Integer userId);

    /**
     * @author Ericson
     * @date 2020/04/15 17:19
     * @param idSet
     * @return
     * @description 查询很多user的name
     */
    @GetMapping("/users/search/name")
    public JsonResult findUsersNameById(@RequestParam(value = "id") Set<Integer> idSet);
}
