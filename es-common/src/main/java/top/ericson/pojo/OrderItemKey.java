package top.ericson.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ericson
 * @class OrderItemKey
 * @date 2020/04/21 13:17
 * @version 1.0
 * @description 订单商品关联表
 */
@Data
@Accessors(chain = true)
@TableName("es_order_item")
public class OrderItemKey implements Serializable{
    
    private static final long serialVersionUID = -176886210886796329L;

    @TableField
    private String orderId;

    @TableField
    private Integer itemId;
    
    private Integer num;
    
}