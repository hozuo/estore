package top.ericson.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.ericson.pojo.Cat;

/**
 * @author Ericson
 * @class InstockMapper
 * @date 2020/03/31 17:14
 * @version 1.0
 * @description 商品分类mapper
 */
@Mapper
public interface CatMapper extends BaseMapper<Cat> {

    List<Cat> selectNamesById(Set<Integer> idSet);

    String selectNameById(Integer id);
    
}
