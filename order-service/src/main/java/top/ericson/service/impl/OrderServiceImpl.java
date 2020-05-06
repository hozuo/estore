package top.ericson.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
import top.ericson.mapper.OrderMapper;
import top.ericson.pojo.Order;
import top.ericson.service.OrderService;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.OrderInfo;

/**
 * @author Ericson
 * @class InstockServiceImpl
 * @date 2020/04/20
 * @version 1.0
 * @description 采购订单服务
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    OrderMapper orderMapper;

    /**
     * @author Ericson
     * @date 2020/04/16 20:07
     * @param orderInfo
     * @return
     * @see top.ericson.service.StoreService#insert(top.ericson.vo.info.StoreInfo)
     * @description 增
     */
    @Override
    public Integer create(OrderInfo orderInfo) {
        Order order = orderInfo.buildPojo();
        Date now = new Date();
        Integer userId = (Integer)request.getAttribute("userId");
        order.setCreateTime(now)
            .setCreateUser(userId)
            .setUpdateTime(now)
            .setUpdateUser(userId);
        return orderMapper.insert(order);
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
    public Integer deleteById(String id) {
        return orderMapper.deleteById(id);
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
    public Integer update(OrderInfo orderInfo) {
        Order order = orderInfo.buildPojo();
        Integer userId = (Integer)request.getAttribute("userId");
        order.setUpdateTime(new Date())
            .setUpdateUser(userId);
        return orderMapper.updateById(order);
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
    public Order findById(String id) {
        return orderMapper.selectById(id);
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
    public IPage<Order> findPage(PageQuery pageQuery) {

        /*开启分页查询*/
        Page<Order> page = new Page<>(pageQuery.getPageCurrent(), pageQuery.getPageSize());

        /*条件构造器*/
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        // 名称查询
        if (pageQuery.getName() != null) {
            queryWrapper.like("order_sn", pageQuery.getName());
        }
        // 排序
        if (pageQuery.getOrderBy() != null) {
            queryWrapper.orderBy(true, pageQuery.getIsASC(), pageQuery.getOrderBy());
        }

        IPage<Order> iPage = orderMapper.selectPage(page, queryWrapper);
        log.debug("iPage:{}", iPage);
        return iPage;
    }

}
