package top.ericson.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import top.ericson.mapper.StockMapper;
import top.ericson.pojo.Stock;
import top.ericson.service.StockService;
import top.ericson.vo.info.InstockInfo;

/**
 * @author Ericson
 * @class StockServiceImpl
 * @date 2020/05/05 19:00
 * @version 1.0
 * @description 
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockMapper stockMapper;

    /**
     * @author Ericson
     * @date 2020/05/05 19:00
     * @param itemId
     * @return
     * @see top.ericson.service.StockService#findByItemId(java.lang.String)
     * @description 
     */
    @Override
    public List<Stock> findByItemId(Integer itemId) {
        QueryWrapper<Stock> wrapper = new QueryWrapper<>();
        wrapper.eq("item_id", itemId);
        return stockMapper.selectList(wrapper);
    }

    /**
     * @author Ericson
     * @date 2020/05/05 19:00
     * @param itemId
     * @return
     * @see top.ericson.service.StockService#deleteByItemId(java.lang.Integer)
     * @description 
     */
    @Override
    public Integer deleteByItemId(Integer itemId) {
        QueryWrapper<Stock> wrapper = new QueryWrapper<>();
        wrapper.eq("item_id", itemId);
        return stockMapper.delete(wrapper);
    }

    /**
     * @author Ericson
     * @date 2020/05/05 19:00
     * @param storeId
     * @return
     * @see top.ericson.service.StockService#deleteByStoreId(java.lang.Integer)
     * @description 
     */
    @Override
    public Integer deleteByStoreId(Integer storeId) {
        return null;
    }

    /**
     * @author Ericson
     * @date 2020/05/05 19:00
     * @param num
     * @return
     * @see top.ericson.service.StockService#enter(java.lang.Long)
     * @description 
     */
    @Override
    public Long enter(InstockInfo instockInfo) {
        // 搜索
        QueryWrapper<Stock> wrapper = new QueryWrapper<>();
        wrapper.eq("item_id", instockInfo.getItemId());
        wrapper.eq("store_id", instockInfo.getStoreId());
        Stock stock = stockMapper.selectOne(wrapper);
        if (stock == null) {
            return null;
        }

        Long enterStore = stock.getEnterStore();
        enterStore += instockInfo.getNum();
        stock.setEnterStore(enterStore);

        Long stockNum = stock.getStock();
        stockNum += instockInfo.getNum();
        stock.setStock(stockNum);

        stockMapper.update(stock, wrapper);
        return stockNum;
    }

    /**
     * @author Ericson
     * @date 2020/05/05 19:00
     * @param num
     * @return
     * @see top.ericson.service.StockService#leave(java.lang.Long)
     * @description 
     */
    @Override
    public Long leave(InstockInfo instockInfo) {
        return null;
    }

    /**
     * @author Ericson
     * @date 2020/05/06 01:55
     * @param itemId
     * @return
     * @see top.ericson.service.StockService#createByItemId(java.lang.Integer)
     * @description 
     */
    @Override
    public Integer createByItemId(Integer itemId) {
        for (int storeId = 1; storeId <= 3; storeId++) {
            stockMapper.insert(new Stock(itemId, storeId, 0L, 0L, 0L));
        }
        return 3;
    }

}
