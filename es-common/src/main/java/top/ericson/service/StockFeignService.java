package top.ericson.service;

import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import top.ericson.vo.JsonResult;

@FeignClient(name = "stock-service")
public interface StockFeignService {
    
    @GetMapping("/stock/searchByItemIds")
    public JsonResult searchByItemIds(@RequestParam(value = "id") Set<Integer> itemIdSet);
    
}