package top.ericson.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.Cat;
import top.ericson.service.CatService;
import top.ericson.service.UserFeignService;
import top.ericson.vo.JsonResult;
import top.ericson.vo.PageObject;
import top.ericson.vo.PageQuery;
import top.ericson.vo.ResultCode;
import top.ericson.vo.info.CatInfo;

/**
 * @author Ericson
 * @class InstockController
 * @date 2020/03/31 17:40
 * @version 1.0
 * @description 商品分类服务控制器
 */
@Slf4j
@RestController
public class CatController {

    @Autowired
    private CatService catService;

    @Autowired
    private UserFeignService userService;

    /**
     * @author Ericson
     * @date 2020/05/04 16:11
     * @param catInfo
     * @return
     * @description [新增]商品分类
     */
    @PostMapping("/cat")
    public JsonResult create(CatInfo catInfo) {
        Integer updateNum = catService.create(catInfo);
        if (updateNum == 1) {
            return JsonResult.success("更新一条记录");
        }
        return JsonResult.success();
    }

    /**
     * @author Ericson
     * @date 2020/05/04 16:10
     * @param id
     * @return
     * @description [删除] 根据id删除商品分类
     */
    @DeleteMapping("/cat/{id}")
    public JsonResult deleteById(@PathVariable("id") Integer id) {
        if (id == null || id == 0) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        Integer deleteNum = catService.deleteById(id);
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
     * @date 2020/05/04 16:11
     * @param catInfo
     * @return
     * @description [修改] 更新商品分类
     */
    @PutMapping("/cat/{id}")
    public JsonResult update(@PathVariable("id") Integer id, CatInfo catInfo) {
        if (id == null || id == 0 || catInfo == null) {
            return JsonResult.build(ResultCode.PARAMS_ERROR);
        }
        catInfo.setCatId(id);
        Integer updateNum = catService.update(catInfo);
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
     * @param catId
     * @return
     * @description [查询] 根据id查询一个商品分类信息
     */
    @GetMapping("/cat/{id}")
    public JsonResult findCatById(@PathVariable("id") Integer catId) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);

        Cat cat = catService.findById(catId);
        if (cat == null) {
            return JsonResult.msg("找不到商品分类");
        }

        // 查userStr
        Set<Integer> userIdSet = new HashSet<>();
        userIdSet.add(cat.getUpdateUser());
        userIdSet.add(cat.getCreateUser());

        JsonResult nameMapResult = userService.findUsersNameById(userIdSet);
        @SuppressWarnings("unchecked")
        Map<String, String> usernameMap = (Map<String, String>)nameMapResult.getData();

        // 返回info
        CatInfo catInfo = new CatInfo(cat, usernameMap);

        return JsonResult.success(catInfo);

    }

    /**
     * @author Ericson
     * @date 2020/04/15 15:01
     * @param catId
     * @return
     * @description [查询] 商品分类名
     */
    @GetMapping("/cat/{id}/name")
    JsonResult findCatNameById(@PathVariable("id") Integer catId) {
        Cat cat = catService.findById(catId);
        if (cat == null) {
            return JsonResult.msg("找不到商品分类");
        } else {
            return JsonResult.success(cat.getCatName());
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
    @GetMapping("/cats")
    public JsonResult findByPage(PageQuery pageQuery) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        String orderBy = CatInfo.orderByCheak(pageQuery.getOrderBy());
        if (orderBy!=null) {
            pageQuery.setOrderBy(orderBy);
        }
        IPage<Cat> iPage = catService.findPage(pageQuery);
        // 获得list
        List<Cat> catList = iPage.getRecords();
        if (catList == null) {
            return JsonResult.fail();
        }
        /*
         * 构造联合查询请求集合
         */
        Set<Integer> userIdSet = new HashSet<>();
        for (Cat cat : catList) {
            userIdSet.add(cat.getUpdateUser());
            userIdSet.add(cat.getCreateUser());
        }
        /*查询用户*/
        JsonResult usersNameJson = userService.findUsersNameById(userIdSet);
        @SuppressWarnings("unchecked")
        Map<String, String> usernameMap = (Map<String, String>)usersNameJson.getData();
        log.debug("usernameMap:{}", usernameMap);
        /*注入值*/
        List<CatInfo> catInfoList = CatInfo.buildInfoList(catList, usernameMap);

        PageObject<CatInfo> pageObject = new PageObject<CatInfo>(iPage, catInfoList);

        return JsonResult.success(pageObject);
    }

    @GetMapping("/cats/search")
    public JsonResult search(@RequestParam("id") Set<Integer> set) {
        log.debug("set:{}", set);
        List<Cat> catList = new ArrayList<>();
        for (Integer i : set) {
            catList.add(catService.findById(i));
        }
        log.debug("catList:{}", catList);
        return JsonResult.success(catList);
    }

    @GetMapping("/cats/search/name")
    public JsonResult findCatsNameById(@RequestParam("id") Set<Integer> idSet) {
        log.debug("set:{}", idSet);
        Map<Integer, String> nameMap = catService.findNamesById(idSet);
        log.debug("nameMap:{}", nameMap);
        return JsonResult.success(nameMap);
    }

}
