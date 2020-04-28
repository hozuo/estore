package top.ericson.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.ericson.pojo.Store;

/**
 * @author Ericson
 * @class StoreMapper
 * @date 2020/04/16 19:40
 * @version 1.0
 * @description 仓库信息mapper
 */
@Mapper
public interface StoreMapper extends BaseMapper<Store> {

    /**
     * @author Ericson
     * @date 2020/04/16 19:40
     * @param name
     * @return
     * @description
     */
    int getRowCount(String name);

    /**
     * @author Ericson
     * @date 2020/04/17 10:43
     * @param idSet
     * @description 查询很多名字
     */
    List<Store> selectStoresNameById(Set<Integer> idSet);

    /**
     * @author Ericson
     * @date 2020/04/17 10:47
     * @param id
     * @return
     * @description 
     */
    String selectStoreNameById(Integer id);

}
