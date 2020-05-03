package top.ericson.service;

import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.core.metadata.IPage;

import top.ericson.pojo.Cat;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.CatInfo;

/**
 * @author Ericson
 * @class CatService
 * @date 2020/05/03 23:56
 * @version 1.0
 * @description 商品分类服务
 */
public interface CatService {
    
    Integer create(CatInfo catInfo);

    Integer deleteById(Integer id);

    Integer update(CatInfo catInfo);

    Cat findById(Integer id);

    IPage<Cat> findPage(PageQuery pageQuery);

    String findNameById(Integer id);

    Map<Integer, String> findNamesById(Set<Integer> idSet);

}
