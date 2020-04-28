package top.ericson.vo.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.ericson.pojo.OrderItemKey;

/**
 * @author Ericson
 * @class OrderItemKeyInfo
 * @date 2020/04/21 13:17
 * @version 1.0
 * @description 订单商品关联表
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemKeyInfo {

    private String orderId;

    private Integer itemId;

    private Integer num;

    /**
     * @date 2020/04/21
     * @author Ericson
     * @param orderItem
     * @description 将pojo对象中的变量赋给info
     */
    public OrderItemKeyInfo(OrderItemKey orderItem) {
        orderId = orderItem.getOrderId();
        itemId = orderItem.getItemId();
        num = orderItem.getNum();
    }

    /**
     * @author Ericson
     * @date 2020/04/21
     * @return
     * @description 
     */
    public OrderItemKey buildPojo() {
        OrderItemKey orderItem = new OrderItemKey();
        orderItem.setOrderId(orderId)
            .setItemId(itemId)
            .setNum(num);
        return orderItem;
    }

    /**
     * @author Ericson
     * @date 2020/04/21
     * @param this
     * @return true代表非空可用
     * @description 判断主要参数是否非空
     */
    public boolean cheak() {
        // true:任意对象为null
        boolean cheakNullflag = orderId != null && itemId != null && num != null;
        return cheakNullflag;
    }

}
