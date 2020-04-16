package top.ericson.service;

import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import top.ericson.vo.JsonResult;

/**
 * @author Ericson
 * @class InstockService
 * @date 2020/03/31 17:36
 * @version 1.0
 * @description 商品feginservice
 */
@FeignClient(name = "item-service")
public interface ItemFeignService {

    @GetMapping("/item/{id}")
    public JsonResult findItemById(@PathVariable("id") Integer itemId);

    /**
     * @author Ericson
     * @date 2020/04/14 21:48
     * @param itemId
     * @return
     * @description 
     */
    @GetMapping("/item/{id}/name")
    public JsonResult findItemNameById(@PathVariable("id") Integer itemId);

    @GetMapping("/items/search/name")
    public JsonResult findItemsNameById(@RequestParam("id") Set<Integer> idSet);
}
