 package top.ericson.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.ericson.pojo.RoleMenuKey;

/**
 * @author Ericson
 * @class MenuMapper
 * @date 2020/05/01
 * @version 1.0
 * @description role_menu
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenuKey> {
    
}
