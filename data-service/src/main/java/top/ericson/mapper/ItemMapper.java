package top.ericson.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.ericson.pojo.Item;

/**
 * @author Ericson
 * @class InstockMapper
 * @date 2020/03/31 17:14
 * @version 1.0
 * @description 商品mapper
 */
@Mapper
public interface ItemMapper extends BaseMapper<Item> {

    /**
     * @author Ericson
     * @param name 商品名称
     * @date 2020/04/04 16:19
     * @return int 总记录数
     * @description 查询总记录数
     */
    int getRowCount(String name);

    /**
     * @author Ericson
     * @date 2020/04/17 18:12
     * @param idSet
     * @return
     * @description 
     */
    List<Item> selectNamesById(Set<Integer> idSet);

    /**
     * @author Ericson
     * @date 2020/04/17 18:12
     * @param id
     * @return
     * @description 
     */
    String selectNameById(Integer id);
    
}
