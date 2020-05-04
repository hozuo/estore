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
import top.ericson.mapper.UserMapper;
import top.ericson.pojo.User;
import top.ericson.service.UserService;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.UserInfo;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    HttpServletRequest request;

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
        List<User> selectList = userMapper.selectList(queryWrapper);
        // 有数据 false 没有 true
        return selectList.isEmpty();
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
    public void createUser(UserInfo userInfo) {
        User user = userInfo.BuildPojo();
        Integer userId = (Integer)request.getAttribute("userId");
        log.debug("userId:{}", userId);
        Date now = new Date();
        user.setCreateTime(now)
            .setCreateUser(userId)
            .setUpdateTime(now)
            .setUpdateUser(userId);
        userMapper.insert(user);
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
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public IPage<User> findPage(PageQuery pageQuery) {

        /*开启分页查询*/
        Page<User> page = new Page<>(pageQuery.getPageCurrent(), pageQuery.getPageSize());
        log.debug("page:{}", page);

        /*条件构造器*/
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        log.debug(pageQuery.getName());

        // 名称查询
        if (pageQuery.getName() != null) {
            queryWrapper.like("username", pageQuery.getName());
        }
        // 排序
        if (pageQuery.getOrderBy() != null && !"".equals(pageQuery.getOrderBy())) {
            queryWrapper.orderBy(true, pageQuery.getIsASC(), pageQuery.getOrderBy());
        }

        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);
        log.debug("iPage:{}", iPage);
        return iPage;
    }

    /**
     * @author Ericson
     * @date 2020/04/29 15:00
     * @param id
     * @return
     * @see top.ericson.service.UserService#deleteById(java.lang.Integer)
     * @description 
     */
    @Override
    public Integer deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    /**
     * @author Ericson
     * @date 2020/04/29 16:01
     * @param id
     * @param userInfo
     * @return
     * @see top.ericson.service.UserService#updateById(java.lang.Integer, top.ericson.vo.info.UserInfo)
     * @description 
     */
    @Override
    public Integer updateById(UserInfo userInfo) {
        User user = userInfo.BuildPojo();
        user.setUserId(userInfo.getUserId());
        Integer userId = (Integer)request.getAttribute("userId");
        user.setUpdateTime(new Date())
            .setUpdateUser(userId);
        return userMapper.updateById(user);
    }

    /**
     * @author Ericson
     * @date 2020/04/29 17:48
     * @param user
     * @return 
     * @see top.ericson.service.UserService#updateValidById(top.ericson.pojo.User)
     * @description 
     */
    @Override
    public Integer updateValidById(User user) {
        Integer userId = (Integer)request.getAttribute("userId");
        user.setUpdateTime(new Date())
            .setUpdateUser(userId);
        return userMapper.updateById(user);
    }

    /**
     * @author Ericson
     * @date 2020/05/03 18:19
     * @param userId
     * @return
     * @see top.ericson.service.UserService#findById(java.lang.Integer)
     * @description 
     */
    @Override
    public User findById(Integer userId) {
        return userMapper.selectById(userId);
    }

    /**
     * @author Ericson
     * @date 2020/05/04 16:57
     * @return
     * @see top.ericson.service.UserService#findAll()
     * @description 
     */
    @Override
    public List<User> findAll() {
        return userMapper.selectList(null);
    }

}
