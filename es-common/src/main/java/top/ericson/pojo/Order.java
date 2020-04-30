package top.ericson.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("es_order")
public class Order extends BasePojo {
    
    private static final long serialVersionUID = -5690955925150824376L;

    @TableId
    private String orderId;

    private String orderSn;

    private Integer supplierId;

    private Integer userId;

    private Integer deptId;

    private Integer storeId;

    private Integer state;
}