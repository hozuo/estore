package top.ericson.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.ericson.pojo.Stock;

/**
 * @author Ericson
 * @class StockMapper
 * @date 2020/05/05 18:45
 * @version 1.0
 * @description 库存
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {
    
}
