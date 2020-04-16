package top.ericson.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.ericson.vo.info.ItemInfo;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("es_item")
@NoArgsConstructor
public class Item extends BasePojo {

    private static final long serialVersionUID = -8628064781640712406L;

    @TableId(type = IdType.AUTO)
    private Integer itemId;

    private String name;

    private String itemSn;

    private Integer catId;

    private Integer specId;

    private Integer unitId;

    private Long buyPrice;

    private Long salePrice;

    private String remark;

    /**
     * @date 2020/04/14
     * @author Ericson
     * @param itemInfo
     * @description info构造pojo
     */
    public Item(ItemInfo itemInfo) {
        itemId = itemInfo.getId();
        name = itemInfo.getName();
        itemSn = itemInfo.getSn();
        catId = itemInfo.getCatId();
        specId = itemInfo.getSpecId();
        unitId = itemInfo.getUnitId();
        buyPrice = itemInfo.getBuyprice();
        salePrice = itemInfo.getSaleprice();
        remark = itemInfo.getRemark();
    }

}