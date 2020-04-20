package top.ericson.service.impl;

import java.util.Set;

import org.springframework.stereotype.Component;

import top.ericson.service.DataFeignService;
import top.ericson.vo.JsonResult;


@Component
public class DataFeignServiceFB implements DataFeignService {

    /**
     * @author Ericson
     * @date 2020/04/19 17:14
     * @param itemId
     * @return
     * @see top.ericson.service.ItemFeignService#findItemById(java.lang.Integer)
     * @description 
     */
    @Override
    public JsonResult findItemById(Integer itemId) {
        return null;
    }

    /**
     * @author Ericson
     * @date 2020/04/19 17:14
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
     * @date 2020/04/19 17:14
     * @param idSet
     * @return
     * @see top.ericson.service.ItemFeignService#findItemsNameById(java.util.Set)
     * @description 
     */
    @Override
    public JsonResult findItemsNameById(Set<Integer> idSet) {
        return null;
    }

    /**
     * @author Ericson
     * @date 2020/04/19 17:14
     * @param userId
     * @return
     * @see top.ericson.service.ItemFeignService#findStoreNameById(java.lang.Integer)
     * @description 
     */
    @Override
    public JsonResult findStoreNameById(Integer userId) {
        return null;
    }

    /**
     * @author Ericson
     * @date 2020/04/19 17:14
     * @param idSet
     * @return
     * @see top.ericson.service.ItemFeignService#findStoresNameById(java.util.Set)
     * @description 
     */
    @Override
    public JsonResult findStoresNameById(Set<Integer> idSet) {
        return null;
    }


}
