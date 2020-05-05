package top.ericson.vo.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.ericson.pojo.Stock;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockInfo {

    /*商品id*/
    private Integer itemId;

    /*仓库id*/
    private Integer storeId;

    /*仓库名称*/
    private String storeStr;

    /*入库总量*/
    private Long enterStore;

    /*出库总量*/
    private Long leaveStore;

    /*库存量*/
    private Long stock;

    public StockInfo(Stock stock, String storeStr) {
        itemId = stock.getItemId();
        storeId = stock.getStoreId();
        this.storeStr = storeStr;
        enterStore = stock.getEnterStore();
        leaveStore = stock.getLeaveStore();
        this.stock = stock.getStock();
    }

    public static List<StockInfo> buildStockInfoList(List<Stock> stockList, Map<String, String> nameMap) {
        List<StockInfo> infoList = new ArrayList<StockInfo>();
        for (Stock stock : stockList) {
            infoList.add(new StockInfo(stock, nameMap.get(stock.getStoreId().toString())));
        }
        return infoList;
    }

}
