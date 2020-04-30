package top.ericson.service;

import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.core.metadata.IPage;

import top.ericson.pojo.Store;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.StoreInfo;

/**
 * @author Ericson
 * @class StoreService
 * @date 2020/04/16 19:49
 * @version 1.0
 * @description 仓库服务接口,包括增删改查,分页
 */
public interface StoreService {
    
    /**
     * @author Ericson
     * @date 2020/04/16 20:05
     * @param storeInfo
     * @return
     * @description 增
     */
    Integer insert(StoreInfo storeInfo);
    
    /**
     * @author Ericson
     * @date 2020/04/16 20:05
     * @param id
     * @return
     * @description 删
     */
    Integer deleteById(Integer id);

    /**
     * @author Ericson
     * @date 2020/04/16 20:05
     * @param storeInfo
     * @return
     * @description 改
     */
    Integer update(StoreInfo storeInfo);

    /**
     * @author Ericson
     * @date 2020/04/16 20:06
     * @param id
     * @return
     * @description 查
     */
    Store findById(Integer id);

    /**
     * @author Ericson
     * @date 2020/04/16 21:12
     * @param pageQuery
     * @return
     * @description 分页查询
     */
    IPage<Store> findPage(PageQuery pageQuery);

    /**
     * @author Ericson
     * @date 2020/04/16 21:34
     * @param idSet
     * @return
     * @description 查询很多名字
     */
    Map<Integer, String> findNamesById(Set<Integer> idSet);

    /**
     * @author Ericson
     * @date 2020/04/16 21:38
     * @param id
     * @return
     * @description 查询名字
     */
    String findNameById(Integer id);
}
