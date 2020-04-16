package top.ericson.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.Instock;
import top.ericson.service.InstockService;
import top.ericson.service.ItemFeignService;
import top.ericson.service.UserFeignService;
import top.ericson.vo.JsonResult;
import top.ericson.vo.PageObject;
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
    private ItemFeignService itemService;
    
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
    public JsonResult createInstock(InstockInfo instockInfo) {
        /*TODO 数据校验*/
        if (instockInfo == null || !instockInfo.cheak()) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }

        /*校验商品*/
        // 使线程可见,拦截器可以获得请求头中的token
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        JsonResult itemJson = itemService.findItemById(instockInfo.getItemId());
        if (Integer.parseInt(itemJson.getCode()) != 200) {
            return JsonResult.msg("商品校验失败");
        }

        /*TODO 校验仓库*/

        /*TODO 校验用户*/

        /*新建入库流水*/
        Integer createNum = instockService.createInstock(instockInfo);
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
    public JsonResult updateInstock(@PathVariable("id") Integer inId, InstockInfo instockInfo) {
        /*TODO 数据校验*/
        if (inId == null || inId == 0 || instockInfo == null || !instockInfo.cheak()) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }

        /*校验商品*/
        // 使线程可见,拦截器可以获得请求头中的token
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        JsonResult itemJson = itemService.findItemById(instockInfo.getItemId());
        if (Integer.parseInt(itemJson.getCode()) != 200) {
            return JsonResult.msg("商品校验失败");
        }

        /*TODO 校验仓库*/

        /*TODO 校验用户*/

        /*更新入库流水*/
        instockInfo.setId(inId);
        Integer updateNum = instockService.updateInstock(instockInfo);
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
    public JsonResult deleteInstockById(@PathVariable(value = "id") Integer inId) {
        Integer deleteNum = instockService.deleteInstockById(inId);
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
    public JsonResult findInstockById(@PathVariable("id") String instockId) {
        // 根据id查询一条入库流水记录
        Instock instock = instockService.findInstockById(instockId);
        
        // 查itemStr
        Integer itemId = instock.getItemId();
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        JsonResult itemNameResult = itemService.findItemNameById(itemId);
        String itemStr =(String)itemNameResult.getData();
        
        // 查userStr
        Integer userId = instock.getUserId();
        Integer updateUserId = instock.getUpdateUser();
        Integer createUserId = instock.getCreateUser();
        JsonResult userNameResult = userService.findUserNameById(userId);
        JsonResult updateUserNameResult = userService.findUserNameById(updateUserId);
        JsonResult createUserNameResult = userService.findUserNameById(createUserId);
        String userStr =(String)userNameResult.getData();
        String updateUserStr =(String)updateUserNameResult.getData();
        String createUserStr =(String)createUserNameResult.getData();
        
        // 返回info
        InstockInfo instockInfo = new InstockInfo(instock);
        instockInfo.setItemStr(itemStr);
        instockInfo.setUserStr(userStr);
        instockInfo.setUpdateUserStr(updateUserStr);
        instockInfo.setCreateUserStr(createUserStr);
        // TODO instockInfo.setStoreStr(storeStr);
        
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
    @GetMapping("/instock/search")
    @SuppressWarnings("unchecked")
    public JsonResult search(@RequestParam(value = "id", required = false) List<Integer> inIdList,
        @RequestParam(value = "item", required = false) List<Integer> itemIdList,
        @RequestParam(value = "pagesize", defaultValue = "10") Integer pageSize,
        @RequestParam(value = "page", defaultValue = "1") Integer page,
        @RequestParam(value = "orderby", defaultValue = "update_time") String orderBy,
        @RequestParam(value = "ordertype", defaultValue = "DESC") String orderType) {

        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        /*
         * 数据校验
         * TODO:orderBy和orderType是直接注入,要做防注入校验
         */
        if (pageSize > 100) {
            return JsonResult.msg("每页数量超过上限");
        } else if (pageSize <= 0) {
            return JsonResult.fail();
        }
        if (page < 1) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        // id去重
        if (inIdList != null) {
            inIdList = inIdList.stream()
                .distinct()
                .collect(Collectors.toList());
            log.debug("list:{}", inIdList);
        }
        // item去重
        if (itemIdList != null) {
            itemIdList = itemIdList.stream()
                .distinct()
                .collect(Collectors.toList());
            log.debug("itemIdList:{}", itemIdList);
        }
        /*
         * 查询入库流水
         */
        // 查询符合条件的行数
        Integer rowCount = instockService.selectCount(orderBy, orderType, inIdList, itemIdList);
        // 未找到直接返回
        if (rowCount == 0) {
            return JsonResult.msg("未查询到记录");
        }
        // 按条件分页查询
        List<Instock> instockList =
            instockService.findInstockByPage(page, pageSize, orderBy, orderType, inIdList, itemIdList);
        log.debug("instockList:{}", instockList);
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
        JsonResult itemsNameJson = itemService.findItemsNameById(itemIdSet);
        Map<String, String> itemsNameMap = (Map<String, String>)itemsNameJson.getData();
        log.debug("itemsNameMap:{}", itemsNameMap);

        /*TODO查询仓库*/

        /*查询用户*/
        JsonResult usersNameJson= userService.findUsersNameById(userIdSet);
        Map<String, String> usersNameMap = (Map<String, String>)usersNameJson.getData();

        /*注入值*/
        ArrayList<InstockInfo> instockInfoList = new ArrayList<>();
        for (Instock i : instockList) {
            // 遍历入库流水表,新建info,用流水初始化,设置商品名称
            InstockInfo info = new InstockInfo(i);
            String itemStr = (String)itemsNameMap.get(i.getItemId()
                .toString());
            String userStr = (String)usersNameMap.get(i.getUserId()
                .toString());
            String updateUserStr = (String)usersNameMap.get(i.getUpdateUser()
                .toString());
            String createUserStr = (String)usersNameMap.get(i.getCreateUser()
                .toString());
            info.setItemStr(itemStr);
            info.setUserStr(userStr);
            info.setUpdateUserStr(updateUserStr);
            info.setCreateUserStr(createUserStr);
            instockInfoList.add(info);
        }
        return JsonResult.success(
            new PageObject<InstockInfo>(page, pageSize, rowCount, (rowCount - 1) / pageSize + 1, instockInfoList));
    }

}
