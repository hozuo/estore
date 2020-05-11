package top.ericson.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.ericson.pojo.Supplier;

/**
 * @author Ericson
 * @class StoreMapper
 * @date 2020/04/20
 * @version 1.0
 * @description 供应商mapper
 */
@Mapper
public interface SupplierMapper extends BaseMapper<Supplier> {


    int getRowCount(String name);

    /**
     * @author Ericson
     * @date 2020/04/20
     * @param idSet
     * @description 查询很多名字
     */
    List<Supplier> selectNamesById(Set<Integer> idSet);

    /**
     * @author Ericson
     * @date 2020/04/20
     * @param id
     * @return
     * @description 
     */
    String selectNameById(Integer id);

}
