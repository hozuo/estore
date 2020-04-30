 package top.ericson.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.ericson.pojo.Menu;

/**
 * @author Ericson
 * @class MenuMapper
 * @date 2020/04/30
 * @version 1.0
 * @description menu
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    
}
