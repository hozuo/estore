 package top.ericson.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import top.ericson.mapper.CatMapper;
import top.ericson.pojo.Cat;
import top.ericson.service.CatService;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.CatInfo;

/**
 * @author Ericson
 * @class CatServiceImpl
 * @date 2020/05/04 00:00
 * @version 1.0
 * @description 商品分类服务实现
 */
@Service
public class CatServiceImpl implements CatService {
    
    @Autowired
    HttpServletRequest request;

    @Autowired
    CatMapper catMapper;

    /**
     * @author Ericson
     * @date 2020/05/04 00:00
     * @param catInfo
     * @return
     * @see top.ericson.service.CatService#create(top.ericson.vo.info.CatInfo)
     * @description 
     */
    @Override
    public Integer create(CatInfo catInfo) {
        Cat cat = catInfo.buildPojo();
        Integer userId = (Integer)request.getAttribute("userId");
        Date now = new Date();
        cat.setCreateTime(now)
            .setCreateUser(userId)
            .setUpdateTime(now)
            .setUpdateUser(userId);
        return catMapper.insert(cat);
    }

    /**
     * @author Ericson
     * @date 2020/05/04 00:00
     * @param id
     * @return
     * @see top.ericson.service.CatService#deleteById(java.lang.Integer)
     * @description 
     */
    @Override
    public Integer deleteById(Integer id) {
        return catMapper.deleteById(id);
    }

    /**
     * @author Ericson
     * @date 2020/05/04 00:00
     * @param catInfo
     * @return
     * @see top.ericson.service.CatService#update(top.ericson.vo.info.CatInfo)
     * @description 
     */
    @Override
    public Integer update(CatInfo catInfo) {
        Cat cat = catInfo.buildPojo();
        Integer userId = (Integer)request.getAttribute("userId");
        Date now = new Date();
        cat.setUpdateTime(now)
            .setUpdateUser(userId);
        return catMapper.updateById(cat);
    }

    /**
     * @author Ericson
     * @date 2020/05/04 00:00
     * @param id
     * @return
     * @see top.ericson.service.CatService#findById(java.lang.Integer)
     * @description 
     */
    @Override
    public Cat findById(Integer id) {
        return catMapper.selectById(id);
    }

    /**
     * @author Ericson
     * @date 2020/05/04 00:00
     * @param pageQuery
     * @return
     * @see top.ericson.service.CatService#findPage(top.ericson.vo.PageQuery)
     * @description 
     */
    @Override
    public IPage<Cat> findPage(PageQuery pageQuery) {

        /*开启分页查询*/
        Page<Cat> page = new Page<>(pageQuery.getPageCurrent(), pageQuery.getPageSize());

        /*条件构造器*/
        QueryWrapper<Cat> queryWrapper = new QueryWrapper<>();
        // 名称查询
        if (pageQuery.getName() != null) {
            queryWrapper.like("cat_name", pageQuery.getName());
        }
        // 排序
        if (pageQuery.getOrderBy() != null) {
            queryWrapper.orderBy(true, pageQuery.getIsASC(), pageQuery.getOrderBy());
        }

        IPage<Cat> iPage = catMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    /**
     * @author Ericson
     * @date 2020/05/04 00:00
     * @param id
     * @return
     * @see top.ericson.service.CatService#findNameById(java.lang.Integer)
     * @description 
     */
    @Override
    public String findNameById(Integer id) {
        return catMapper.selectNameById(id);
    }

    /**
     * @author Ericson
     * @date 2020/05/04 00:00
     * @param idSet
     * @return
     * @see top.ericson.service.CatService#findNamesById(java.util.Set)
     * @description 
     */
    @Override
    public Map<Integer, String> findNamesById(Set<Integer> idSet) {
        List<Cat> list = catMapper.selectNamesById(idSet);
        Map<Integer, String> nameMap = new HashMap<Integer, String>();
        for (Cat cat : list) {
            if (cat != null && cat.getCatName() != null) {
                nameMap.put(cat.getCatId(), cat.getCatName());
            }
        }
        return nameMap;
    }

}
