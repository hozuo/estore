 package top.ericson.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.ericson.pojo.Role;

/**
 * @author Ericson
 * @class RoleMapper
 * @date 2020/04/09 16:38
 * @version 1.0
 * @description role
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    
    public List<Role> selectRolesNameById(Set<Integer> idSet);
    
    public String selectRoleNameById(Integer id);
    
}
