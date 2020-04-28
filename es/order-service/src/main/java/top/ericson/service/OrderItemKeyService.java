package top.ericson.service;

import java.util.List;

import top.ericson.pojo.OrderItemKey;
import top.ericson.vo.info.OrderItemKeyInfo;

public interface OrderItemKeyService {

    Integer create(OrderItemKeyInfo orderItemKeyInfo);

    Integer deleteByOrderId(String id);

    Integer deleteByItemId(String id);

    Integer update(OrderItemKeyInfo orderItemKeyInfo);

    List<OrderItemKey> findByOrderId(String id);

    List<OrderItemKey> findByItemId(String id);

}