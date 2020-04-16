package top.ericson.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.ericson.pojo.Item;

/**
 * @author Ericson
 * @class InstockMapper
 * @date 2020/03/31 17:14
 * @version 1.0
 * @description 入库流水mapper
 */
@Mapper
public interface StoreMapper extends BaseMapper<Item> {

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
     * @date 2020/04/04 16:41
     * @param startIndex 起始下标
     * @param pageSize 每页的行数,也是查询的个数
     * @param name 查询的名称,可以为""
     * @return List<Item> 一页商品的列表
     * @description 查询一页
     */
    List<Item> findPageObjects(Integer startIndex, Integer pageSize, String name);
    
}
