 package top.ericson.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.ericson.pojo.User;

/**
 * @author Ericson
 * @class UserMapper
 * @date 2020/04/09 16:38
 * @version 1.0
 * @description user
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    public List<User> selectUsersNameById(Set<Integer> idSet);
    
    public String selectUserNameById(Integer id);
    
}
