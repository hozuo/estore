package top.ericson.vo.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.ericson.pojo.Item;

/**
 * @author Ericson
 * @class ItemStockInfo
 * @date 2020/05/05 20:59
 * @version 1.0
 * @description 
 */
@Data
@NoArgsConstructor
public class ItemStockInfo implements Serializable {

    private static final long serialVersionUID = 1650767369447307598L;

    /**
     * @date 2020/05/05
     * @author Ericson
     * @param item
     * @param arrayList<Store> 
     * @description 
     */
    @SuppressWarnings("rawtypes")
    public static List<ItemStockInfo> buildItemStockInfoList(Item item, ArrayList<LinkedHashMap> stockList,String storeName,
        List<ItemStockInfo> infoList) {
        for (LinkedHashMap stock : stockList) {
            // 在infoList中添加一条全纪录
            infoList.add(new ItemStockInfo(item.getItemId(), item.getItemName(), stock.get("storeId"),
                storeName, stock.get("enterStore"), stock.get("leaveStore"), stock.get("stock")));
        }
        return infoList;
    }

    private Integer itemId;

    private String itemName;

    private Integer storeId;

    private String storeName;

    private Long enterStore;

    private Long leaveStore;

    private Long stock;

    /**
     * @date 2020/05/05
     * @author Ericson
     * @param @param itemId
     * @param @param itemName
     * @param @param storeId
     * @param @param storeName
     * @param @param enterStore
     * @param @param leaveStore
     * @param @param stock
     * @description 
     */
    public ItemStockInfo(Integer itemId, String itemName, Object storeId, Object storeName, Object enterStore,
        Object leaveStore, Object stock) {
        super();
        this.itemId = itemId;
        this.itemName = itemName;
        this.storeId = (Integer)storeId;
        this.storeName = (String)storeName;
        this.enterStore = Long.parseLong(enterStore.toString());
        this.leaveStore = Long.parseLong(leaveStore.toString());
        this.stock = Long.parseLong(stock.toString());
    }

}
