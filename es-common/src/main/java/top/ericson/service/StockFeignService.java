package top.ericson.service;

import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import top.ericson.vo.JsonResult;

@FeignClient(name = "stock-service")
public interface StockFeignService {
    
    @GetMapping("/stock/searchByItemIds")
    public JsonResult searchByItemIds(@RequestParam(value = "id") Set<Integer> itemIdSet);
    
    @PostMapping("/stock/{id}")
    public JsonResult createStockByItemId(@PathVariable("id") Integer itemId);
    
    @DeleteMapping("/stock/{id}")
    public JsonResult deleteStockByItemId(@PathVariable("id") Integer itemId);
    
    
}