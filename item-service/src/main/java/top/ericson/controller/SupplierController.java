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
import top.ericson.pojo.Supplier;
import top.ericson.service.SupplierService;
import top.ericson.service.UserFeignService;
import top.ericson.vo.JsonResult;
import top.ericson.vo.PageObject;
import top.ericson.vo.PageQuery;
import top.ericson.vo.ResultCode;
import top.ericson.vo.info.StoreInfo;
import top.ericson.vo.info.SupplierInfo;

/**
 * @author Ericson
 * @class InstockController
 * @date 2020/04/20
 * @version 1.0
 * @description 供应商控制器
 */
@Slf4j
@RestController
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private UserFeignService userService;

    /**
     * @author Ericson
     * @date 2020/04/20
     * @param supplierInfo
     * @return
     * @description [新增]
     */
    @PostMapping("/supplier")
    public JsonResult create(SupplierInfo supplierInfo) {
        if (supplierInfo == null || !supplierInfo.cheak()) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        Integer insertNum = supplierService.create(supplierInfo);
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
    @DeleteMapping("/supplier/{id}")
    public JsonResult deleteById(@PathVariable("id") Integer id) {
        if (id == 0) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        Integer deleteNum = supplierService.deleteById(id);
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
    @PutMapping("/supplier/{id}")
    public JsonResult update(@PathVariable("id") Integer id, SupplierInfo supplierInfo) {
        if (id == 0 || supplierInfo == null) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        supplierInfo.setId(id);
        Integer updateNum = supplierService.update(supplierInfo);
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
    @GetMapping("/supplier/{id}")
    public JsonResult findById(@PathVariable("id") Integer id) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        if (id == 0) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        Supplier supplier = supplierService.findById(id);
        if (supplier == null) {
            return JsonResult.msg("找不到记录");
        }
        Set<Integer> idSet = new HashSet<>();
        idSet.add(supplier.getUpdateUser());
        idSet.add(supplier.getCreateUser());
        @SuppressWarnings("unchecked")
        Map<String, String> nameMap = (Map<String, String>)userService.findUsersNameById(idSet)
            .getData();
        SupplierInfo supplierInfo = new SupplierInfo(supplier, nameMap);
        return JsonResult.success(supplierInfo);
    }

    /**
     * @author Ericson
     * @date 2020/04/15 15:01
     * @param id
     * @return
     * @description [查询] 名字
     */
    @GetMapping("/supplier/{id}/name")
    JsonResult findNameById(@PathVariable("id") Integer id) {
        String name = supplierService.findNameById(id);
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
    @GetMapping("/suppliers")
    public JsonResult findByPage(PageQuery pageQuery) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        // 判断orderBy是否合法
        if (!pageQuery.cheak(new StoreInfo().getClass())) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        // 分页查询
        IPage<Supplier> iPage = supplierService.findPage(pageQuery);
        if (iPage == null) {
            return JsonResult.fail();
        }
        // 获得list
        List<Supplier> supplierList = iPage.getRecords();
        if (supplierList == null) {
            return JsonResult.fail();
        }
        // 构建联合查询集合
        Set<Integer> idSet = new HashSet<>();
        for (Supplier s : supplierList) {
            idSet.add(s.getUpdateUser());
            idSet.add(s.getCreateUser());
        }
        // 联合查询用户名
        JsonResult userJson = userService.findUsersNameById(idSet);
        @SuppressWarnings("unchecked")
        Map<String, String> usernameMap = (Map<String, String>)userJson.getData();
        // 构造infolist
        List<SupplierInfo> supplierInfoList = SupplierInfo.buildInfoList(supplierList, usernameMap);

        PageObject<SupplierInfo> pageObject = new PageObject<SupplierInfo>(iPage, supplierInfoList);

        return JsonResult.success(pageObject);
    }

    /**
     * @author Ericson
     * @date 2020/04/19 15:53
     * @param idSet
     * @return
     * @description 查询很多名字
     */
    @GetMapping("/suppliers/search/name")
    public JsonResult findNamesById(@RequestParam("id") Set<Integer> idSet) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        if (idSet == null || idSet.size() == 0) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        log.debug("idSet:{}", idSet);
        Map<Integer, String> nameMap = new LinkedHashMap<Integer, String>();
        nameMap = supplierService.findNamesById(idSet);
        log.debug("nameMap:{}", nameMap);
        return JsonResult.success(nameMap);
    }

}
