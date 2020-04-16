package top.ericson.service.impl;

import java.util.Set;

import org.springframework.stereotype.Component;

import top.ericson.service.ItemFeignService;
import top.ericson.vo.JsonResult;


@Component
public class ItemFeignServiceFB implements ItemFeignService {

    /**
     * @author Ericson
     * @date 2020/04/05 03:03
     * @param itemId
     * @return
     * @see top.ericson.service.ItemFeignService#findItemById(java.lang.Integer)
     * @description 
     */
    @Override
    public JsonResult findItemById(Integer itemId) {
        System.out.println("ItemFeignServiceFB.findItemById()");
        return null;
    }

    /**
     * @author Ericson
     * @date 2020/04/14 21:57
     * @param itemId
     * @return
     * @see top.ericson.service.ItemFeignService#findItemNameById(java.lang.Integer)
     * @description 
     */
    @Override
    public JsonResult findItemNameById(Integer itemId) {
        return null;
    }

    /**
     * @author Ericson
     * @date 2020/04/14 22:01
     * @param idSet
     * @return
     * @see top.ericson.service.ItemFeignService#findItemsNameById(java.util.Set)
     * @description 
     */
    @Override
    public JsonResult findItemsNameById(Set<Integer> idSet) {
        return null;
    }

}
