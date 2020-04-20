package top.ericson.controller;

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
import top.ericson.pojo.Store;
import top.ericson.service.StoreService;
import top.ericson.service.UserFeignService;
import top.ericson.vo.JsonResult;
import top.ericson.vo.PageObject;
import top.ericson.vo.PageQuery;
import top.ericson.vo.ResultCode;
import top.ericson.vo.info.StoreInfo;

/**
 * @author Ericson
 * @class InstockController
 * @date 2020/03/31 17:40
 * @version 1.0
 * @description 商品服务控制器
 */
@Slf4j
@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserFeignService userService;

    /**
     * @author Ericson
     * @date 2020/04/16 21:18
     * @param storeInfo
     * @return
     * @description [新增]
     */
    @PostMapping("/store")
    public JsonResult create(StoreInfo storeInfo) {
        Integer insertNum = storeService.insert(storeInfo);
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
     * @date 2020/04/16 21:18
     * @param id
     * @return
     * @description [删除] 根据id删除
     */
    @DeleteMapping("/store/{id}")
    public JsonResult deleteById(@PathVariable("id") Integer id) {
        if (id == 0) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        Integer deleteNum = storeService.deleteById(id);
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
     * @date 2020/04/14 16:11
     * @param itemInfo
     * @return
     * @description [修改] 更新
     */
    @PutMapping("/store/{id}")
    public JsonResult update(@PathVariable("id") Integer id, StoreInfo storeInfo) {
        if (id == 0 || storeInfo == null) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        storeInfo.setId(id);
        Integer updateNum = storeService.update(storeInfo);
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
     * @date 2020/04/05 00:22
     * @param id
     * @return
     * @description [查询] 根据id查询一条记录
     */
    @GetMapping("/store/{id}")
    public JsonResult findById(@PathVariable("id") Integer id) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        if (id == 0) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        Store store = storeService.findById(id);
        if (store == null) {
            return JsonResult.msg("找不到记录");
        }
        Set<Integer> idSet = new HashSet<>();
        idSet.add(store.getManager());
        idSet.add(store.getUpdateUser());
        idSet.add(store.getCreateUser());
        Map<String, String> nameMap = (Map<String, String>)userService.findUsersNameById(idSet)
            .getData();
        StoreInfo storeInfo = new StoreInfo(store, nameMap);
        return JsonResult.success(storeInfo);
    }

    /**
     * @author Ericson
     * @date 2020/04/15 15:01
     * @param id
     * @return
     * @description [查询] 名字
     */
    @GetMapping("/store/{id}/name")
    JsonResult findStoreNameById(@PathVariable("id") Integer id) {
        String name = storeService.findNameById(id);
        if (name == null) {
            return JsonResult.msg("找不到记录");
        } else {
            return JsonResult.success(name);
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
    @GetMapping("/stores")
    public JsonResult findByPage(PageQuery pageQuery) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        // 判断orderBy是否合法
        if (!pageQuery.cheak(new StoreInfo().getClass())) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        // 分页查询
        IPage<Store> iPage = storeService.findPage(pageQuery);
        if (iPage == null) {
            return JsonResult.fail();
        }
        // 获得list
        List<Store> storeList = iPage.getRecords();
        if (storeList == null) {
            return JsonResult.fail();
        }
        // 构建联合查询集合
        Set<Integer> idSet = new HashSet<>();
        for (Store s : storeList) {
            idSet.add(s.getManager());
            idSet.add(s.getUpdateUser());
            idSet.add(s.getCreateUser());
        }
        // 联合查询用户名
        JsonResult userJson = userService.findUsersNameById(idSet);
        @SuppressWarnings("unchecked")
        Map<String, String> usernameMap = (Map<String, String>)userJson.getData();
        // 构造infolist
        List<StoreInfo> storeInfoList = StoreInfo.buildInfoList(storeList, usernameMap);

        PageObject<StoreInfo> pageObject = new PageObject<StoreInfo>(iPage, storeInfoList);

        return JsonResult.success(pageObject);
    }

    /**
     * @author Ericson
     * @date 2020/04/19 15:53
     * @param idSet
     * @return
     * @description 查询很多名字
     */
    @GetMapping("/stores/search/name")
    public JsonResult findStoresNameById(@RequestParam("id") Set<Integer> idSet) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        if (idSet == null || idSet.size() == 0) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        log.debug("idSet:{}", idSet);
        Map<Integer, String> nameMap = new LinkedHashMap<Integer, String>();
        nameMap = storeService.findNamesById(idSet);
        log.debug("nameMap:{}", nameMap);
        return JsonResult.success(nameMap);
    }

}
