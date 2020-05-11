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
import top.ericson.pojo.Supplier;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.SupplierInfo;
import top.ericson.mapper.SupplierMapper;
import top.ericson.service.SupplierService;

/**
 * @author Ericson
 * @class InstockServiceImpl
 * @date 2020/04/20
 * @version 1.0
 * @description 仓库服务
 */
@Slf4j
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    SupplierMapper supplierMapper;

    /**
     * @author Ericson
     * @date 2020/04/16 20:07
     * @param supplierInfo
     * @return
     * @see top.ericson.service.StoreService#insert(top.ericson.vo.info.StoreInfo)
     * @description 增
     */
    @Override
    public Integer create(SupplierInfo supplierInfo) {
        Supplier supplier = supplierInfo.buildPojo();
        Date now = new Date();
        Integer userId = (Integer)request.getAttribute("userId");
        supplier.setCreateTime(now)
            .setCreateUser(userId)
            .setUpdateTime(now)
            .setUpdateUser(userId);
        return supplierMapper.insert(supplier);
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
        return supplierMapper.deleteById(id);
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
    public Integer update(SupplierInfo supplierInfo) {
        Supplier supplier = supplierInfo.buildPojo();
        Integer userId = (Integer)request.getAttribute("userId");
        supplier.setUpdateTime(new Date())
            .setUpdateUser(userId);
        return supplierMapper.updateById(supplier);
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
    public Supplier findById(Integer id) {
        return supplierMapper.selectById(id);
    }

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
    public IPage<Supplier> findPage(PageQuery pageQuery) {

        /*开启分页查询*/
        Page<Supplier> page = new Page<>(pageQuery.getPageCurrent(), pageQuery.getPageSize());

        /*条件构造器*/
        QueryWrapper<Supplier> queryWrapper = new QueryWrapper<>();
        // 名称查询
        if (pageQuery.getName() != null) {
            queryWrapper.like("name", pageQuery.getName());
        }
        // 排序
        if (pageQuery.getOrderBy() != null) {
            queryWrapper.orderBy(true, pageQuery.getIsASC(), pageQuery.getOrderBy());
        }

        IPage<Supplier> iPage = supplierMapper.selectPage(page, queryWrapper);
        log.debug("iPage:{}", iPage);
        return iPage;
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
        return supplierMapper.selectNameById(id);
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
        List<Supplier> supplierList = supplierMapper.selectNamesById(idSet);
        Map<Integer, String> nameMap = new HashMap<>();
        for (Supplier s : supplierList) {
            nameMap.put(s.getSupplierId(), s.getName());
        }
        return nameMap;
    }

}
