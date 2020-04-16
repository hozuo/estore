package top.ericson.service;

import top.ericson.pojo.Item;
import top.ericson.vo.PageObject;
import top.ericson.vo.info.ItemInfo;

/**
 * @author Ericson
 * @class InstockService
 * @date 2020/03/31 17:36
 * @version 1.0
 * @description 商品service
 */
public interface ItemService {

    /**
     * @author Ericson
     * @date 2020/03/31 20:39
     * @param startIndex 起始下标
     * @param pageSize 每页的行数,也是查询的个数
     * @param name 查询的名称,可以为""
     * @return findItemByPage
     * @description 查询一页商品
     */
    PageObject<Item> findItemsByPage(Integer pageCurrent, Integer pageSize, String name);
    
    Item findItemById(Integer ItemId);
    
    Integer insertItem(ItemInfo itemInfo);

    /**
     * @author Ericson
     * @date 2020/04/14 15:55
     * @param itemId
     * @return
     * @description 
     */
    Integer deleteItemById(Integer id);

    /**
     * @author Ericson
     * @date 2020/04/14 16:07
     * @param itemInfo
     * @description 
     */
    Integer updateItem(ItemInfo itemInfo);
}
