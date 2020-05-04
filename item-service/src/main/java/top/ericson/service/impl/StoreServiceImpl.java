package top.ericson.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.Store;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.StoreInfo;
import top.ericson.mapper.StoreMapper;
import top.ericson.service.StoreService;

/**
 * @author Ericson
 * @class InstockServiceImpl
 * @date 2020/03/31 20:37
 * @version 1.0
 * @description 仓库服务
 */
@Slf4j
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreMapper storeMapper;
    
    @Autowired
    HttpServletRequest request;

    /**
     * @author Ericson
     * @date 2020/04/16 20:07
     * @param start
     * @param rows
     * @param orderBy
     * @param orderType
     * @param name
     * @return
     * @see top.ericson.service.StoreService#findPage(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
     * @description 分页
     */
    @Override
    public IPage<Store> findPage(PageQuery pageQuery) {

        /*开启分页查询*/
        Page<Store> page = new Page<>(pageQuery.getPageCurrent(), pageQuery.getPageSize());
        log.debug("page:{}", page);

        /*条件构造器*/
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        // 名称查询
        if (pageQuery.getName() != null) {
            queryWrapper.like("store_name", pageQuery.getName());
        }
        // 排序
        if (pageQuery.getOrderBy() != null) {
            queryWrapper.orderBy(true, pageQuery.getIsASC(), pageQuery.getOrderBy());
        }

        IPage<Store> iPage = storeMapper.selectPage(page, queryWrapper);
        log.debug("iPage:{}", iPage);
        return iPage;
    }

    /**
     * @author Ericson
     * @date 2020/04/16 20:07
     * @param storeInfo
     * @return
     * @see top.ericson.service.StoreService#insert(top.ericson.vo.info.StoreInfo)
     * @description 增
     */
    @Override
    public Integer insert(StoreInfo storeInfo) {
        Store store = storeInfo.buildPojo();
        Integer userId = (Integer)request.getAttribute("userId");
        log.debug("userId:{}", userId);
        Date now = new Date();
        store.setCreateTime(now)
            .setCreateUser(userId)
            .setUpdateTime(now)
            .setUpdateUser(userId);
        return storeMapper.insert(store);
    }

    /**
     * @author Ericson
     * @date 2020/04/16 20:07
     * @param id
     * @return
     * @see top.ericson.service.StoreService#deleteById(java.lang.Integer)
     * @description 删
     */
    @Override
    public Integer deleteById(Integer id) {
        return storeMapper.deleteById(id);
    }

    /**
     * @author Ericson
     * @date 2020/04/16 20:07
     * @param storeInfo
     * @return
     * @see top.ericson.service.StoreService#update(top.ericson.vo.info.StoreInfo)
     * @description 改
     */
    @Override
    public Integer update(StoreInfo storeInfo) {
        return storeMapper.updateById(storeInfo.buildPojo());
    }

    /**
     * @author Ericson
     * @date 2020/04/16 20:07
     * @param id
     * @return
     * @see top.ericson.service.StoreService#findById(java.lang.Integer)
     * @description 查
     */
    @Override
    public Store findById(Integer id) {
        return storeMapper.selectById(id);
    }

    /**
     * @author Ericson
     * @date 2020/04/17 
     * @param idSet
     * @return nameMap
     * @see top.ericson.service.StoreService#findNamesById(java.lang.Integer)
     * @description 查很多名字
     */
    @Override
    public Map<Integer, String> findNamesById(Set<Integer> idSet) {
        List<Store> storeList = storeMapper.selectStoresNameById(idSet);
        Map<Integer, String> nameMap=new HashMap<>();
        for (Store store : storeList) {
            nameMap.put(store.getStoreId(), store.getStoreName());
        }
        return nameMap;
    }

    /**
     * @author Ericson
     * @date 2020/04/17
     * @param id
     * @return
     * @see top.ericson.service.StoreService#findNameById(java.lang.Integer)
     * @description 查一个名字
     */
    @Override
    public String findNameById(Integer id) {
        return storeMapper.selectStoreNameById(id);
    }

}
