package top.ericson.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.Item;
import top.ericson.pojo.Order;
import top.ericson.pojo.OrderItemKey;
import top.ericson.service.DataFeignService;
import top.ericson.service.OrderItemKeyService;
import top.ericson.service.OrderService;
import top.ericson.service.UserFeignService;
import top.ericson.vo.JsonResult;
import top.ericson.vo.PageObject;
import top.ericson.vo.PageQuery;
import top.ericson.vo.ResultCode;
import top.ericson.vo.info.OrderInfo;
import top.ericson.vo.info.OrderItemKeyInfo;

/**
 * @author Ericson
 * @class InstockController
 * @date 2020/04/20
 * @version 1.0
 * @description 订单控制器
 */
@Slf4j
@RestController
public class OrderController {

    @Autowired
    private DataFeignService dataService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserFeignService userService;

    @Autowired
    private OrderItemKeyService orderItemKeyService;

    /**
     * @author Ericson
     * @date 2020/04/20
     * @param orderInfo
     * @return
     * @description [新增]
     */
    @PostMapping("/order")
    public JsonResult create(OrderInfo orderInfo, @RequestParam(value = "list") String keyInfoList) {
        String uuid = UUID.randomUUID()
            .toString()
            .replaceAll("-", "");
        orderInfo.setId(uuid);
        log.debug("orderInfo:{}", orderInfo);
        log.debug("keyInfoList:{}", keyInfoList);
        ObjectMapper mapper = new ObjectMapper();
            List<OrderItemKeyInfo> readValue;
            try {
                readValue = mapper.readValue("[" + keyInfoList + "]", new TypeReference<List<OrderItemKeyInfo>>() {});
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return JsonResult.msg("JSON转换失败");
            }
            log.debug("readValue:{}", readValue);
            for (OrderItemKeyInfo key : readValue) {
                orderItemKeyService.create(key);
            }
        Integer insertNum = orderService.create(orderInfo);

        if (insertNum == 1) {
            return JsonResult.success("新增1条记录");
        } else if (insertNum == 0) {
            return JsonResult.msg("新增0条记录");
        } else {
            return JsonResult.fail();
        }
    }

    /**
     * @author Ericson
     * @date 2020/04/20
     * @param id
     * @return
     * @description [删除] 根据id删除
     */
    @DeleteMapping("/order/{id}")
    public JsonResult deleteById(@PathVariable("id") String id) {
        if (id == "") {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        Integer deleteNum = orderService.deleteById(id);
        if (deleteNum == 1) {
            return JsonResult.success("成功删除1条记录");
        } else if (deleteNum == 0) {
            return JsonResult.msg("找不到记录");
        } else {
            return JsonResult.build(ResultCode.SYS_ERROR);
        }
    }

    /**
     * @author Ericson
     * @date 2020/04/20
     * @return
     * @description [修改] 更新
     */
    @PutMapping("/order/{id}")
    public JsonResult update(@PathVariable("id") String id, OrderInfo orderInfo) {
        if (id == "" || orderInfo == null) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        orderInfo.setId(id);
        Integer updateNum = orderService.update(orderInfo);
        if (updateNum == 1) {
            return JsonResult.success("成功更新1条记录");
        } else if (updateNum == 0) {
            return JsonResult.msg("找不到记录");
        } else {
            return JsonResult.fail();
        }
    }

    /**
     * @author Ericson
     * @date 2020/04/20
     * @param id
     * @return
     * @description [查询] 根据id查询一条记录
     */
    @GetMapping("/order/{id}")
    public JsonResult findById(@PathVariable("id") String id) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        if (id == "") {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        Order order = orderService.findById(id);
        if (order == null) {
            return JsonResult.msg("找不到记录");
        }
        Set<Integer> idSet = new HashSet<>();
        idSet.add(order.getUpdateUser());
        idSet.add(order.getCreateUser());
        // 联合查询
        @SuppressWarnings("unchecked")
        Map<String, String> usernameMap = (Map<String, String>)userService.findUsersNameById(idSet)
            .getData();
        String storeName = (String)dataService.findStoreNameById(order.getStoreId())
            .getData();
        String supplierName = (String)dataService.findSupplierNameById(order.getStoreId())
            .getData();

        OrderInfo orderInfo = new OrderInfo(order, usernameMap);

        orderInfo.setSupplierStr(supplierName)
            .setStoreStr(storeName);
        return JsonResult.success(orderInfo);
    }

    /*复数资源*/

    /**
     * @author Ericson
     * @date 2020/04/20
     * @param startIndex 起始下标
     * @param pageSize 每页的行数,也是查询的个数
     * @param name 查询的名称,可以为""
     * @return JsonResult
     * @description 分页查询
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/orders")
    public JsonResult findByPage(PageQuery pageQuery) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        // 分页查询
        IPage<Order> iPage = orderService.findPage(pageQuery);
        if (iPage == null) {
            return JsonResult.fail();
        }
        // 获得list
        List<Order> orderList = iPage.getRecords();
        if (orderList == null) {
            return JsonResult.fail();
        }
        // 构建联合查询集合
        Set<Integer> useridSet = new HashSet<>();
        Set<Integer> storeIdSet = new HashSet<>();
        Set<Integer> supplierIdSet = new HashSet<>();
        for (Order s : orderList) {
            useridSet.add(s.getUserId());
            useridSet.add(s.getUpdateUser());
            useridSet.add(s.getCreateUser());
            storeIdSet.add(s.getStoreId());
            supplierIdSet.add(s.getSupplierId());
        }
        // 联合查询
        JsonResult userJson = userService.findUsersNameById(useridSet);
        Map<String, String> usernameMap = (Map<String, String>)userJson.getData();

        JsonResult storeNameJson = dataService.findStoresNameById(storeIdSet);
        Map<String, String> storeNameMap = (Map<String, String>)storeNameJson.getData();

        JsonResult supplierNameJson = dataService.findSuppliersNameById(supplierIdSet);
        Map<String, String> supplierNameMap = (Map<String, String>)supplierNameJson.getData();

        // 构造infolist
        List<OrderInfo> orderInfoList =
            OrderInfo.buildInfoList(orderList, supplierNameMap, usernameMap, null, storeNameMap);

        PageObject<OrderInfo> pageObject = new PageObject<OrderInfo>(iPage, orderInfoList);

        return JsonResult.success(pageObject);
    }

    @GetMapping("/order/{id}/items")
    public List<Item> findItemsById(@PathVariable("id") String id) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        List<OrderItemKey> findByOrderId = orderItemKeyService.findByOrderId(id);
        log.debug("findByOrderId:{}", findByOrderId);
        Set<Integer> idSet = new HashSet<>();
        for (OrderItemKey key : findByOrderId) {
            idSet.add(key.getItemId());
        }
        JsonResult itemsJson = dataService.searchItems(idSet);
        List<Item> itemList = (List<Item>)itemsJson.getData();
        return itemList;
    }

}
