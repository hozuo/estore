package top.ericson.vo.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfo extends BaseInfo {

    private static final long serialVersionUID = 6901152961467590104L;

    /*商品id*/
    private Integer itemId;

    /*商品名称*/
    private String itemName;

    /*商品序列号*/
    private String sn;

    /*商品分类id*/
    private Integer catId;

    /*商品分类*/
    private String catStr;

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
        item.setItemId(itemId)
            .setItemName(itemName)
            .setItemSn(sn)
            .setCatId(catId)
            .setRemark(remark)
            .setBuyPrice(buyPrice)
            .setSalePrice(salePrice);
        return item;
    }

    public ItemInfo(Item item) {
        itemId = item.getItemId();
        itemName = item.getItemName();
        sn = item.getItemSn();
        catId = item.getCatId();
        buyPrice = item.getBuyPrice();
        salePrice = item.getSalePrice();
        remark = item.getRemark();
        updateTime = item.getUpdateTime();
        updateUserId = item.getUpdateUser();
        createTime = item.getCreateTime();
        createUserId = item.getCreateUser();
    }

    /**
     * @author Ericson
     * @date 2020/05/04 18:08
     * @param orderBy
     * @return
     * @description 
     */
    public static String orderByCheak(String orderBy) {
        if (orderBy == null || "".equals(orderBy)) {
            return null;
        }
        switch (orderBy) {
            case "itemId":
                return "item_id";
            case "itemName":
                return "item_name";
            case "sn":
                return "item_sn";
            case "catStr":
                return "cat_id";
            case "buyPrice":
                return "buy_price";
            case "salePrice":
                return "sale_price";
            case "remark":
                return "remark";
            case "updateUserStr":
                return "update_user";
            case "updateTime":
                return "update_time";
            case "createUserStr":
                return "create_user";
            case "createTime":
                return "create_time";
            default:
                return null;
        }
    }

    public static List<ItemInfo> buildInfoList(List<Item> itemList, Map<String, String> usernameMap,
        Map<Integer, String> catNameMap) {
        List<ItemInfo> infoList = new ArrayList<ItemInfo>();
        ItemInfo info;
        for (Item i : itemList) {
            info = new ItemInfo(i);
            info.setCatStr(catNameMap.get(info.getCatId()))
                .setUpdateUserStr(usernameMap.get(info.getUpdateUserId()
                    .toString()))
                .setCreateUserStr(usernameMap.get(info.getCreateUserId()
                    .toString()));
            infoList.add(info);
        }
        return infoList;
    }

}
