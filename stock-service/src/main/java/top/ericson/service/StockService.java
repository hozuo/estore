package top.ericson.service;

import java.util.List;

import top.ericson.pojo.Stock;
import top.ericson.vo.info.InstockInfo;

/**
 * @author Ericson
 * @class StockService
 * @date 2020/05/05 18:46
 * @version 1.0
 * @description 库存
 */
public interface StockService {

    List<Stock> findByItemId(Integer itemId);

    Integer deleteByItemId(Integer itemId);

    Integer deleteByStoreId(Integer storeId);

    Long enter(InstockInfo instockInfo);

    Long leave(InstockInfo instockInfo);

}
