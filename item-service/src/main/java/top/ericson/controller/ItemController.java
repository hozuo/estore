package top.ericson.controller;

import java.util.ArrayList;
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

import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.Item;
import top.ericson.service.ItemService;
import top.ericson.vo.JsonResult;
import top.ericson.vo.PageQuery;
import top.ericson.vo.ResultCode;
import top.ericson.vo.info.ItemInfo;

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

    /**
     * @author Ericson
     * @date 2020/04/14 16:11
     * @param itemInfo
     * @return
     * @description [新增]商品
     */
    @PostMapping("/item")
    public JsonResult create(ItemInfo itemInfo) {
        return JsonResult.success(itemService.create(itemInfo));
    }

    /**
     * @author Ericson
     * @date 2020/04/14 16:10
     * @param id
     * @return
     * @description [删除] 根据id删除商品
     */
    @DeleteMapping("/item/{id}")
    public JsonResult deleteById(@PathVariable("id") Integer id) {
        if (id == null || id == 0) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        Integer deleteNum = itemService.deleteById(id);
        if (deleteNum == 1) {
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
        itemInfo.setId(id);
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
            return JsonResult.success(item.getName());
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
        return JsonResult.success(itemService.findPage(pageQuery));
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

}
