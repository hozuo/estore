package top.ericson.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.ericson.vo.info.InstockInfo;

/**
 * @author Ericson
 * @class Instock
 * @date 2020/03/31 16:38
 * @version 1.0
 * @description 入库订单实体,对应es_instock表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("es_instock")
@AllArgsConstructor
@NoArgsConstructor
public class Instock extends BasePojo {

    private static final long serialVersionUID = 5380095088071966495L;

    // 入库id
    @TableId(type = IdType.AUTO)
    private Integer inId;
    // 入库编号
    private String inSn;
    // 仓库id
    private Integer storeId;
    // 用户id
    private Integer userId;
    // 商品id
    private Integer itemId;
    // 采购订单id
    private String buyId;
    // 流程状态
    private Integer inState;
    // 入库数量
    private Long num;
    // 入库时间
    private Date inTime;
    // 实时库存(变动前)
    private Long stock;

    /**
     * @date 2020/04/14
     * @author Ericson
     * @param info
     * @description 使用info构造pojo
     */
    public Instock(InstockInfo info) {
        inSn = info.getSn();
        storeId = info.getStoreId();
        userId = info.getUserId();
        itemId = info.getItemId();
        buyId = info.getBuyId();
        inState = info.getInState();
        num = info.getNum();
        inTime = info.getInTime();
        stock = info.getStock();
    }

}
