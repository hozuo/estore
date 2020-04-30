package top.ericson.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;
import top.ericson.mapper.OrderItemKeyMapper;
import top.ericson.pojo.OrderItemKey;
import top.ericson.service.OrderItemKeyService;
import top.ericson.vo.info.OrderItemKeyInfo;

/**
 * @author Ericson
 * @class InstockServiceImpl
 * @date 2020/04/20
 * @version 1.0
 * @description 采购订单服务
 */
@Service
public class OrderItemKeyServiceImpl implements OrderItemKeyService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    OrderItemKeyMapper orderItemKeyMapper;

    /**
     * @author Ericson
     * @date 2020/04/21 15:05
     * @param orderItemKeyInfo
     * @return
     * @see top.ericson.service.OrderItemKeyService#create(top.ericson.vo.info.OrderItemKeyInfo)
     * @description 
     */
    @Override
    public Integer create(OrderItemKeyInfo orderItemKeyInfo) {
        OrderItemKey entity = orderItemKeyInfo.buildPojo();
        return orderItemKeyMapper.insert(entity);
    }

    /**
     * @author Ericson
     * @date 2020/04/21 15:05
     * @param id
     * @return
     * @see top.ericson.service.OrderItemKeyService#deleteByOrderId(java.lang.String)
     * @description 
     */
    @Override
    public Integer deleteByOrderId(String id) {
        QueryWrapper<OrderItemKey> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", id);
        return orderItemKeyMapper.delete(wrapper);
    }

    /**
     * @author Ericson
     * @date 2020/04/21 15:05
     * @param id
     * @return
     * @see top.ericson.service.OrderItemKeyService#deleteByItemId(java.lang.String)
     * @description 
     */
    @Override
    public Integer deleteByItemId(String id) {
        QueryWrapper<OrderItemKey> wrapper = new QueryWrapper<>();
        wrapper.eq("item_id", id);
        return orderItemKeyMapper.delete(wrapper);
    }

    /**
     * @author Ericson
     * @date 2020/04/21 15:05
     * @param orderItemKeyInfo
     * @return
     * @see top.ericson.service.OrderItemKeyService#update(top.ericson.vo.info.OrderItemKeyInfo)
     * @description 
     */
    @Override
    public Integer update(OrderItemKeyInfo orderItemKeyInfo) {
        return orderItemKeyMapper.updateById(orderItemKeyInfo.buildPojo());
    }

    /**
     * @author Ericson
     * @date 2020/04/21 15:05
     * @param id
     * @return
     * @see top.ericson.service.OrderItemKeyService#findByOrderId(java.lang.String)
     * @description 
     */
    @Override
    public List<OrderItemKey> findByOrderId(String id) {
        QueryWrapper<OrderItemKey> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", id);
        return orderItemKeyMapper.selectList(wrapper);
    }

    /**
     * @author Ericson
     * @date 2020/04/21 15:05
     * @param id
     * @return
     * @see top.ericson.service.OrderItemKeyService#findByItemId(java.lang.String)
     * @description 
     */
    @Override
    public List<OrderItemKey> findByItemId(String id) {
        QueryWrapper<OrderItemKey> wrapper = new QueryWrapper<>();
        wrapper.eq("item_id", id);
        return orderItemKeyMapper.selectList(wrapper);
    }

}
