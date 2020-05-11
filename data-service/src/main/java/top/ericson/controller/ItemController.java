package top.ericson.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.Item;
import top.ericson.service.CatService;
import top.ericson.service.ItemService;
import top.ericson.service.StockFeignService;
import top.ericson.service.StoreService;
import top.ericson.service.UserFeignService;
import top.ericson.vo.JsonResult;
import top.ericson.vo.PageObject;
import top.ericson.vo.PageQuery;
import top.ericson.vo.ResultCode;
import top.ericson.vo.info.ItemInfo;
import top.ericson.vo.info.ItemStockInfo;

/**
 * @author Ericson
 * @class InstockController
 * @date 2020/03/31 17:40
 * @version 1.0
 * @description 商品服务控制器
 */
@Slf4j
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CatService catService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserFeignService userService;

    @Autowired
    private StockFeignService stockService;

    /**
     * @author Ericson
     * @date 2020/04/14 16:11
     * @param itemInfo
     * @return
     * @description [新增]商品
     */
    @PostMapping("/item")
    public JsonResult create(ItemInfo itemInfo) {
        
        Integer createNum = itemService.create(itemInfo);
        if (createNum==1) {
            return JsonResult.success();
        }
        return JsonResult.fail();
    }

    /**
     * @author Ericson
     * @date 2020/04/14 16:10
     * @param itemId
     * @return
     * @description [删除] 根据id删除商品
     */
    @DeleteMapping("/item/{id}")
    public JsonResult deleteById(@PathVariable("id") Integer itemId) {
        if (itemId == null || itemId == 0) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        Integer deleteNum = itemService.deleteById(itemId);
        if (deleteNum == 1) {
            
            stockService.deleteStockByItemId(itemId);
            return JsonResult.success("成功删除1条数据");
        } else if (deleteNum == 0) {
            return JsonResult.msg("找不到数据");
        } else {
            return JsonResult.build(ResultCode.SYS_ERROR);
        }
    }

    /**
     * @author Ericson
     * @date 2020/04/14 16:11
     * @param itemInfo
     * @return
     * @description [修改] 更新商品
     */
    @PutMapping("/item/{id}")
    public JsonResult update(@PathVariable("id") Integer id, ItemInfo itemInfo) {
        if (id == null || id == 0 || itemInfo == null) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        itemInfo.setItemId(id);
        Integer updateNum = itemService.update(itemInfo);
        if (updateNum == 1) {
            return JsonResult.success("成功更新1条数据");
        } else if (updateNum == 0) {
            return JsonResult.msg("找不到数据");
        } else {
            return JsonResult.build(ResultCode.SYS_ERROR);
        }
    }

    /**
     * @author Ericson
     * @date 2020/04/05 00:22
     * @param itemId
     * @return
     * @description [查询] 根据id查询一个商品信息
     */
    @GetMapping("/item/{id}")
    public JsonResult findItemById(@PathVariable("id") Integer itemId) {
        Item item = itemService.findById(itemId);
        if (item == null) {
            return JsonResult.msg("找不到商品");
        } else {
            return JsonResult.success(item);
        }
    }

    /**
     * @author Ericson
     * @date 2020/04/15 15:01
     * @param itemId
     * @return
     * @description [查询] 商品名
     */
    @GetMapping("/item/{id}/name")
    JsonResult findItemNameById(@PathVariable("id") Integer itemId) {
        Item item = itemService.findById(itemId);
        if (item == null) {
            return JsonResult.msg("找不到商品");
        } else {
            return JsonResult.success(item.getItemName());
        }
    }

    /*复数资源*/

    /**
     * @author Ericson
     * @date 2020/04/03 13:55
     * @param startIndex 起始下标
     * @param pageSize 每页的行数,也是查询的个数
     * @param name 查询的名称,可以为""
     * @return JsonResult
     * @description 分页查询
     */
    @GetMapping("/items")
    public JsonResult findByPage(PageQuery pageQuery) {
        
        String orderBy = ItemInfo.orderByCheak(pageQuery.getOrderBy());
        if (orderBy != null) {
            pageQuery.setOrderBy(orderBy);
        }
        IPage<Item> iPage = itemService.findPage(pageQuery);
        // 获得list
        List<Item> itemList = iPage.getRecords();
        if (itemList == null) {
            return JsonResult.fail();
        }
        /*
         * 构造联合查询请求集合
         */
        Set<Integer> userIdSet = new HashSet<>();
        Set<Integer> catIdSet = new HashSet<>();
        for (Item item : itemList) {
            catIdSet.add(item.getCatId());
            userIdSet.add(item.getUpdateUser());
            userIdSet.add(item.getCreateUser());
        }
        /*查询用户*/
        JsonResult usersNameJson = userService.findUsersNameById(userIdSet);
        @SuppressWarnings("unchecked")
        Map<String, String> usernameMap = (Map<String, String>)usersNameJson.getData();
        log.debug("usernameMap:{}", usernameMap);

        /*查询分类*/
        Map<Integer, String> catNameMap = catService.findNamesById(catIdSet);
        log.debug("catNameMap:{}", catNameMap);

        /*注入值*/
        List<ItemInfo> itemInfoList = ItemInfo.buildInfoList(itemList, usernameMap, catNameMap);

        PageObject<ItemInfo> pageObject = new PageObject<ItemInfo>(iPage, itemInfoList);

        return JsonResult.success(pageObject);
    }

    @GetMapping("/items/search")
    public JsonResult search(@RequestParam("id") Set<Integer> set) {
        log.debug("set:{}", set);
        List<Item> itemList = new ArrayList<>();
        for (Integer i : set) {
            itemList.add(itemService.findById(i));
        }
        log.debug("itemList:{}", itemList);
        return JsonResult.success(itemList);
    }

    @GetMapping("/items/search/name")
    public JsonResult findItemsNameById(@RequestParam("id") Set<Integer> idSet) {
        log.debug("set:{}", idSet);
        Map<Integer, String> nameMap = itemService.findNamesById(idSet);
        log.debug("nameMap:{}", nameMap);
        return JsonResult.success(nameMap);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @GetMapping("/items/search/stock")
    public JsonResult findItemStocksById(PageQuery pageQuery) {
        
        String orderBy = ItemInfo.orderByCheak(pageQuery.getOrderBy());
        if (orderBy != null) {
            pageQuery.setOrderBy(orderBy);
        }
        IPage<Item> iPage = itemService.findPage(pageQuery);
        // 获得list
        List<Item> itemList = iPage.getRecords();
        if (itemList == null) {
            return JsonResult.fail();
        }
        /*
         * 构造联合查询请求集合
         */
        Set<Integer> itemIdSet = new HashSet<>();
        for (Item item : itemList) {
            itemIdSet.add(item.getItemId());
        }

        JsonResult stockJson = stockService.searchByItemIds(itemIdSet);
        // data:Map<Integer商品, ArrayList<Stock>多仓库存>
        LinkedHashMap<String, ArrayList<LinkedHashMap>> stockMap =
            (LinkedHashMap<String, ArrayList<LinkedHashMap>>)stockJson.getData();

        Set<Integer> idSet = new HashSet<>();
        for (int i = 1; i <= 100; i++) {
            idSet.add(i);
        }
        Map<Integer, String> nameMap = storeService.findNamesById(idSet);

        List<ItemStockInfo> itemStockInfoList = new ArrayList<>();
        for (Item item : itemList) {
            // 这个商品对应的多仓库存
            itemStockInfoList = ItemStockInfo.buildItemStockInfoList(item, stockMap.get(item.getItemId()
                .toString()), nameMap, itemStockInfoList);
        }

        PageObject<ItemStockInfo> pageObject = new PageObject<ItemStockInfo>(iPage, itemStockInfoList);

        /*注入值*/
        return JsonResult.success(pageObject);
    }

}
