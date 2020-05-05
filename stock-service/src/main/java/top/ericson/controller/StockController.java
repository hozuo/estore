package top.ericson.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

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
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);

        // 定义返回的map集合
        Map<Integer, List<Stock>> stockMap = new HashMap<Integer, List<Stock>>();

        // 遍历itemId查询库存信息
        for (Integer itemId : itemIdSet) {
            stockMap.put(itemId, stockService.findByItemId(itemId));
        }

        return JsonResult.success(stockMap);
    }

}
