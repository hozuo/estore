package top.ericson.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.ericson.pojo.Instock;

/**
 * @author Ericson
 * @class InstockMapper
 * @date 2020/03/31 17:14
 * @version 1.0
 * @description 入库流水mapper
 */
@Mapper
public interface InstockMapper extends BaseMapper<Instock> {
    

    /**
     * @author Ericson
     * @date 2020/03/31 20:50
     * @param start
     * @param rows
     * @return
     * @description 分页查询
     */
    List<Instock> findInstockByPage(Integer start, Integer rows,String orderBy,String orderType,List<Integer> inIdList,List<Integer> itemIdList);

    /**
     * @author Ericson
     * @date 2020/04/03 13:56
     * @return
     * @description 有条件搜索总数
     */
    int selectCount(String orderBy,String orderType,List<Integer> inIdList,List<Integer> itemIdList);

    /**
     * @author Ericson
     * @date 2020/04/08 20:55
     * @return
     * @description 无条件搜索总数,mybatis+实现
     */
    Integer selectCount();

}
