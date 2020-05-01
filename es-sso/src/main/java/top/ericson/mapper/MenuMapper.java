 package top.ericson.mapper;

import java.util.List;
import java.util.Set;

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
    
    public List<Menu> selectNamesById(Set<Integer> idSet);

    /**
     * @author Ericson
     * @date 2020/05/01 18:01
     * @param idSet
     * @return
     * @description 
     */
    public List<Menu> selectById(Set<Integer> idSet);
    
}
