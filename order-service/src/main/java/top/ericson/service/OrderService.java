package top.ericson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import top.ericson.pojo.Order;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.OrderInfo;

public interface OrderService {

    Integer create(OrderInfo orderInfo);

    Integer deleteById(String id);

    Integer update(OrderInfo orderInfo);

    Order findById(String id);

    IPage<Order> findPage(PageQuery pageQuery);

}