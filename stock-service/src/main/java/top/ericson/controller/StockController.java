package top.ericson.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import top.ericson.pojo.Stock;
import top.ericson.service.DataFeignService;
import top.ericson.service.StockService;
import top.ericson.vo.JsonResult;

/**
 * @author Ericson
 * @class StockController
 * @date 2020/05/05 19:05
 * @version 1.0
 * @description 
 */
@RestController
public class StockController {

    @Autowired
    StockService stockService;

    @Autowired
    DataFeignService dataService;

    @GetMapping("/stock/searchByItemIds")
    public JsonResult searchByItemIds(@RequestParam(value = "id") Set<Integer> itemIdSet) {
        // 定义返回的map集合
        Map<Integer, List<Stock>> stockMap = new HashMap<Integer, List<Stock>>();
        List<Stock> stockList;
        // 遍历itemId查询库存信息
        for (Integer itemId : itemIdSet) {
            stockList = stockService.findByItemId(itemId);
            if (stockList == null || stockList.isEmpty()) {
                stockService.createByItemId(itemId);
                stockList = stockService.findByItemId(itemId);
            }
            stockMap.put(itemId, stockList);
        }

        return JsonResult.success(stockMap);
    }

    @PostMapping("/stock/{id}")
    public JsonResult createStockByItemId(@PathVariable("id") Integer itemId) {
        stockService.createByItemId(itemId);
        return JsonResult.success();
    }

    @DeleteMapping("/stock/{id}")
    public JsonResult deleteStockByItemId(@PathVariable("id") Integer itemId) {
        stockService.deleteByItemId(itemId);
        return JsonResult.success();
    }
}
