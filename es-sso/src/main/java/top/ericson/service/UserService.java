package top.ericson.service;

import java.util.Map;
import java.util.Set;

import top.ericson.pojo.User;

/**
 * @author Ericson
 * @class UserService
 * @date 2020/04/09 16:06
 * @version 1.0
 * @description user服务
 */
public interface UserService {

    /**
     * @author Ericson
     * @date 2020/04/09 16:07
     * @param param
     * @param type 1:username 2:phone 3:email
     * @return
     * @description 校验一个用户数据是否已经存在
     */
    boolean checkUser(String param, Integer type);

    /**
     * @author Ericson
     * @date 2020/04/09 22:12
     * @param username
     * @param password
     * @param email
     * @param phone
     * @param invitation
     * @description 
     */
    void createUser(String username, String password, String email, String phone, String invitation);

    /**
     * @author Ericson
     * @date 2020/04/15 15:10
     * @param userId
     * @description 
     */
    String findUserNameById(Integer userId);

    /**
     * @author Ericson
     * @date 2020/04/15 15:37
     * @param idSet
     * @description 
     */
    Map<Integer, String> findUsersNameById(Set<Integer> idSet);

    /**
     * @author Ericson
     * @date 2020/04/17 08:08
     * @param username
     * @return 
     * @description 
     */
    User findByName(String username);

}
