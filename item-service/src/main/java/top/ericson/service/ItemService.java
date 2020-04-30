package top.ericson.service;

import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.core.metadata.IPage;

import top.ericson.pojo.Item;
import top.ericson.vo.PageQuery;
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
     * @date 2020/04/17 18:03
     * @param pageQuery
     * @return
     * @description 
     */
    IPage<Item> findPage(PageQuery pageQuery);

    /**
     * @author Ericson
     * @date 2020/04/17 18:05
     * @param id
     * @return
     * @description 
     */
    Integer deleteById(Integer id);

    /**
     * @author Ericson
     * @date 2020/04/17 18:05
     * @param itemInfo
     * @return
     * @description 
     */
    Integer update(ItemInfo itemInfo);

    /**
     * @author Ericson
     * @date 2020/04/17 18:05
     * @param id
     * @return
     * @description 
     */
    Item findById(Integer id);

    /**
     * @author Ericson
     * @date 2020/04/17 18:06
     * @param idSet
     * @return
     * @description 
     */
    Map<Integer, String> findNamesById(Set<Integer> idSet);

    /**
     * @author Ericson
     * @date 2020/04/17 18:06
     * @param id
     * @return
     * @description 
     */
    String findNameById(Integer id);

    /**
     * @author Ericson
     * @date 2020/04/17 18:11
     * @param itemInfo
     * @return
     * @description 
     */
    Object create(ItemInfo itemInfo);
}
