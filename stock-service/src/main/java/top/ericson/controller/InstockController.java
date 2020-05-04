package top.ericson.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.Instock;
import top.ericson.service.InstockService;
import top.ericson.service.DataFeignService;
import top.ericson.service.UserFeignService;
import top.ericson.vo.JsonResult;
import top.ericson.vo.PageObject;
import top.ericson.vo.PageQuery;
import top.ericson.vo.ResultCode;
import top.ericson.vo.info.InstockInfo;

/**
 * @author Ericson
 * @class InstockController
 * @date 2020/03/31 17:40
 * @version 1.0
 * @description 入库控制器
 */
@Slf4j
@RestController
public class InstockController {

    @Autowired
    private InstockService instockService;

    @Autowired
    private DataFeignService dataService;

    @Autowired
    private UserFeignService userService;

    /**
     * @author Ericson
     * @date 2020/04/14 18:24
     * @param instockInfo
     * @return JsonResult
     * @description 新建
     */
    @PostMapping("/instock")
    public JsonResult create(InstockInfo instockInfo) {
        log.debug("instockInfo:{}", instockInfo);
        /*TODO 数据校验*/

        /*校验商品*/
        // 使线程可见,拦截器可以获得请求头中的token
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);

        /*TODO 校验仓库*/

        /*TODO 校验用户*/

        /*新建入库流水*/
        Integer createNum = instockService.create(instockInfo);
        if (createNum == 1) {
            return JsonResult.success("新建一条记录");
        } else {
            return JsonResult.fail();
        }
    }

    /**
     * @author Ericson
     * @date 2020/04/14 19:07
     * @return
     * @description 修改
     */
    @PutMapping("/instock/{id}")
    public JsonResult update(@PathVariable("id") Integer inId, InstockInfo instockInfo) {
        /*TODO 数据校验*/
        if (inId == null || inId == 0 || instockInfo == null || !instockInfo.cheak()) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }

        /*校验商品*/
        // 使线程可见,拦截器可以获得请求头中的token
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        JsonResult itemJson = dataService.findItemById(instockInfo.getItemId());
        if (Integer.parseInt(itemJson.getStatus()) != 200) {
            return JsonResult.msg("商品校验失败");
        }

        /*TODO 校验仓库*/

        /*TODO 校验用户*/

        /*更新入库流水*/
        instockInfo.setId(inId);
        Integer updateNum = instockService.update(instockInfo);
        if (updateNum == 1) {
            return JsonResult.success("更新一条记录");
        } else {
            return JsonResult.fail();
        }
    }

    /**
     * @author Ericson
     * @date 2020/04/09 00:49
     * @param inId
     * @return
     * @description 删除记录
     */
    @DeleteMapping("/instock/{id}")
    public JsonResult deleteById(@PathVariable(value = "id") Integer inId) {
        Integer deleteNum = instockService.deleteById(inId);
        if (deleteNum == 1) {
            return JsonResult.success("删除一条记录");
        } else {
            return JsonResult.fail();
        }
    }

    /**
     * @author Ericson
     * @date 2020/04/05 00:49
     * @param inStockId
     * @return
     * @description 根据id查询一个入库流水
     */
    @GetMapping("/instock/{id}")
    public JsonResult findById(@PathVariable("id") String instockId) {
        // 根据id查询一条入库流水记录
        Instock instock = instockService.findById(instockId);

        // 查itemStr
        Integer itemId = instock.getItemId();
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        JsonResult itemNameResult = dataService.findItemNameById(itemId);
        String itemStr = (String)itemNameResult.getData();

        // 查storeStr
        Integer storeId = instock.getStoreId();
        JsonResult storeNameResult = dataService.findStoreNameById(storeId);
        String storeStr = (String)storeNameResult.getData();

        // 查userStr
        Set<Integer> userIdSet = new HashSet<>();
        userIdSet.add(instock.getUserId());
        userIdSet.add(instock.getUpdateUser());
        userIdSet.add(instock.getCreateUser());

        JsonResult nameMapResult = userService.findUsersNameById(userIdSet);
        @SuppressWarnings("unchecked")
        Map<String, String> nameMap = (Map<String, String>)nameMapResult.getData();

        // 返回info
        InstockInfo instockInfo = new InstockInfo(instock, nameMap);
        instockInfo.setItemStr(itemStr);
        instockInfo.setStoreStr(storeStr);

        return JsonResult.success(instockInfo);
    }

    /*复数资源*/
    /**
     * @author Ericson
     * @date 2020/04/08 18:52
     * @param inIdList
     * @param itemIdList
     * @param pageSize
     * @param page
     * @param orderBy
     * @param orderType
     * @return JsonResult
     * @description 很牛逼的多字段联合分页查询
     */
    @GetMapping("/instocks")
    @SuppressWarnings("unchecked")
    public JsonResult search(PageQuery pageQuery) {

        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        
        /*TODO 入库流水排序搜索*/
        
        /*
         * 查询入库流水
         */
        IPage<Instock> iPage = instockService.findPage(pageQuery);
        List<Instock> instockList = iPage.getRecords();

        /*
         * 构造联合查询请求集合
         */
        Set<Integer> userIdSet = new HashSet<>();
        Set<Integer> itemIdSet = new HashSet<>();
        Set<Integer> storeIdSet = new HashSet<>();
        for (Instock instock : instockList) {
            userIdSet.add(instock.getUserId());
            userIdSet.add(instock.getUpdateUser());
            userIdSet.add(instock.getCreateUser());
            itemIdSet.add(instock.getItemId());
            storeIdSet.add(instock.getStoreId());
        }
        log.debug("instockList:{}", instockList);
        log.debug("userIdSet:{}", userIdSet);
        log.debug("itemIdSet:{}", itemIdSet);
        log.debug("storeIdSet:{}", storeIdSet);

        /*查询商品*/
        JsonResult itemsNameJson = dataService.findItemsNameById(itemIdSet);
        Map<String, String> itemsNameMap = (Map<String, String>)itemsNameJson.getData();
        log.debug("itemsNameMap:{}", itemsNameMap);

        /*查询仓库*/
        JsonResult storesNameJson = dataService.findStoresNameById(storeIdSet);
        Map<String, String> storesNameMap = (Map<String, String>)storesNameJson.getData();
        log.debug("storesNameMap:{}", storesNameMap);

        /*查询用户*/
        JsonResult usersNameJson = userService.findUsersNameById(userIdSet);
        Map<String, String> usernameMap = (Map<String, String>)usersNameJson.getData();

        /*注入值*/
        List<InstockInfo> instockInfoList = InstockInfo.buildInfoList(instockList, usernameMap ,itemsNameMap, storesNameMap);
        
        PageObject<InstockInfo> pageObject = new PageObject<InstockInfo>(iPage, instockInfoList);
        
        return JsonResult.success(pageObject);
    }

}
