package top.ericson.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;
import top.ericson.mapper.UserMapper;
import top.ericson.pojo.User;
import top.ericson.service.UserService;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @author Ericson
     * @date 2020/04/09 16:07
     * @param param 用户需要校验的数据
     * @param type 校验的类型 1username 2 phone 3 email
     * @return boolean
     * @see top.ericson.service.UserService#checkUser(java.lang.String, java.lang.Integer)
     * @description
     */
    @Override
    public boolean checkUser(String param, Integer type) {
        String column = (type == 1 ? "username" : (type == 2 ? "phone" : "email"));
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq(column, param);
        int count = userMapper.selectCount(queryWrapper);
        // 有数据 true 没有 false
        return (count > 0 ? true : false);
    }

    /**
     * @author Ericson
     * @date 2020/04/09 22:15
     * @param username
     * @param password
     * @param email
     * @param phone
     * @param invitation
     * @see top.ericson.service.UserService#createUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     * @description 
     */
    @Override
    public void createUser(String username, String password, String email, String phone, String invitation) {
        User user = new User();

        user.setUsername(username)
            .setEmail(email)
            .setPhone(phone)
            .setInvitation(invitation);
    }

    /**
     * @author Ericson
     * @date 2020/04/15 15:10
     * @param userId
     * @return
     * @see top.ericson.service.UserService#findUserNameById(java.lang.Integer)
     * @description 
     */
    @Override
    public String findNameById(Integer id) {
        return userMapper.selectUserNameById(id);
    }

    /**
     * @author Ericson
     * @date 2020/04/15 15:37
     * @param idSet
     * @see top.ericson.service.UserService#findUsersNameById(java.util.Set)
     * @description 
     */
    @Override
    public Map<Integer, String> findNamesById(Set<Integer> idSet) {
        log.debug("idSet:{}", idSet);
        List<User> userList = userMapper.selectUsersNameById(idSet);
        HashMap<Integer, String> usersNameMap = new HashMap<Integer, String>();
        for (User user : userList) {
            usersNameMap.put(user.getUserId(), user.getUsername());
        }
        return usersNameMap;
    }

    /**
     * @author Ericson
     * @date 2020/04/17 08:09
     * @param username
     * @see top.ericson.service.UserService#findByName(java.lang.String)
     * @description 
     */
    @Override
    public User findByName(String username) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }
}
