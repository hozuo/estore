package top.ericson.vo.info;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.ericson.pojo.Item;

/**
 * @author Ericson
 * @class ItemInfo
 * @date 2020/04/14 15:02
 * @version 1.0
 * @description item传值对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ItemInfo extends BaseInfo {

    private static final long serialVersionUID = 6901152961467590104L;

    /*商品id*/
    private Integer id;

    /*商品名称*/
    private String name;

    /*商品序列号*/
    private String sn;

    /*商品分类id*/
    private Integer catId;

    /*商品分类*/
    private String catStr;

    /*商品规格id*/
    private Integer specId;

    /*商品规格*/
    private String specStr;

    /*商品单位id*/
    private Integer unitId;

    /*商品单位*/
    private String unitStr;

    /*进价*/
    private Long buyPrice;

    /*售价*/
    private Long salePrice;

    /*备注*/
    private String remark;

    /**
     * @date 2020/04/14
     * @author Ericson
     * @param itemInfo
     * @description info构造pojo
     */
    public Item buildPojo() {
        Item item = new Item();
        item.setItemId(id)
            .setName(name)
            .setItemSn(sn)
            .setCatId(catId)
            .setSpecId(specId)
            .setUnitId(unitId)
            .setBuyPrice(buyPrice)
            .setSalePrice(salePrice);
        return item;
    }

}
