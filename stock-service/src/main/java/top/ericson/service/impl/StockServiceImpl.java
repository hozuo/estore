package top.ericson.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import top.ericson.mapper.StockMapper;
import top.ericson.pojo.Stock;
import top.ericson.service.StockService;

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
        return null;
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
    public Long enter(Long num) {
        return null;
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
    public Long leave(Long num) {
        return null;
    }

}
