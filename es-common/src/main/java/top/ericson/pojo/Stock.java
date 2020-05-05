package top.ericson.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author Ericson
 * @class Stock
 * @date 2020/05/05 17:41
 * @version 1.0
 * @description 库存表
 */
@Data
@TableName("es_stock")
public class Stock {

    /*商品id*/
    @TableField
    private Integer itemId;

    /*仓库id*/
    @TableField
    private Integer storeId;

    /*入库总量*/
    private Long enterStore;

    /*出库总量*/
    private Long leaveStore;

    /*库存量*/
    private Long stock;

    /**
     * @author Ericson
     * @date 2020/05/05 18:26
     * @param num
     * @return
     * @description 入库
     */
    public Long enter(Long num) {
        if (num == null) {
            return null;
        }
        enterStore += num;
        return stock += num;
    }

    /**
     * @author Ericson
     * @date 2020/05/05 18:28
     * @param num
     * @return
     * @description 出库
     */
    public Long leave(Long num) {
        if (num == null) {
            return null;
        }
        leaveStore += num;
        stock -= num;
        if (stock < 0L) {
            stock = 0L;
        }
        return stock;
    }

}
