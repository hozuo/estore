package top.ericson.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.Instock;
import top.ericson.mapper.InstockMapper;
import top.ericson.service.InstockService;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.InstockInfo;

/**
 * @author Ericson
 * @class InstockServiceImpl
 * @date 2020/03/31 20:37
 * @version 1.0
 * @description 
 */
@Slf4j
@Service
public class InstockServiceImpl implements InstockService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    InstockMapper instockMapper;

    /**
     * @author Ericson
     * @date 2020/04/05 00:45
     * @param inStockId
     * @return
     * @see top.ericson.service.InstockService#findInStockById(java.lang.String)
     * @description 查
     */
    @Override
    public Instock findById(String inStockId) {
        return instockMapper.selectById(inStockId);
    }

    /**
     * @author Ericson
     * @date 2020/04/08 20:55
     * @return
     * @see top.ericson.service.InstockService#selectCount()
     * @description 查数量
     */
    @Override
    public Integer selectCount() {
        return instockMapper.selectCount();
    }

    /**
     * @author Ericson
     * @date 2020/04/08 23:26
     * @see top.ericson.service.InstockService#createInstock(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Long, java.util.Date, java.lang.Long, java.util.Date, java.lang.Integer, java.util.Date, java.lang.Integer)
     * @description 增
     */
    @Override
    public Integer create(Instock instock) {
        Date now = new Date();
        Integer userId = (Integer)request.getAttribute("userId");
        instock.setCreateTime(now)
            .setCreateUser(userId)
            .setUpdateTime(now)
            .setUpdateUser(userId);
        return instockMapper.insert(instock);
    }

    /**
     * @author Ericson
     * @date 2020/04/09 00:46
     * @param inId
     * @see top.ericson.service.InstockService#deleteInstockById(java.lang.Integer)
     * @description 删
     */
    @Override
    public Integer deleteById(Integer inId) {
        return instockMapper.deleteById(inId);
    }

    /**
     * @author Ericson
     * @date 2020/04/14 18:41
     * @param instockInfo
     * @return
     * @see top.ericson.service.InstockService#create(top.ericson.vo.info.InstockInfo)
     * @description 增
     */
    @Override
    public Integer create(InstockInfo instockInfo) {
        Instock instock = new Instock(instockInfo);
        log.debug("instock:{}", instock);
        return this.create(instock);
    }

    /**
     * @author Ericson
     * @date 2020/04/14 19:11
     * @param instockInfo
     * @return
     * @see top.ericson.service.InstockService#updateInstock(top.ericson.vo.info.InstockInfo)
     * @description 改
     */
    @Override
    public Integer update(InstockInfo instockInfo) {
        Instock instock = new Instock(instockInfo);
        instock.setInId(instockInfo.getId())
            .setUpdateUser((Integer)request.getAttribute("userId"))
            .setUpdateTime(new Date());
        return instockMapper.updateById(instock);
    }

    /**
     * @author Ericson
     * @date 2020/04/17 17:44
     * @param pageQuery
     * @return
     * @see top.ericson.service.InstockService#findPage(top.ericson.vo.PageQuery)
     * @description 查
     */
    @Override
    public IPage<Instock> findPage(PageQuery pageQuery) {
        /*开启分页查询*/
        Page<Instock> page = new Page<>(pageQuery.getPageCurrent(), pageQuery.getPageSize());
        log.debug("page:{}", page);
        
        /*条件构造器*/
        QueryWrapper<Instock> queryWrapper = new QueryWrapper<>();
        // 名称查询
        if (pageQuery.getName() != null) {
            queryWrapper.like("in_sn", pageQuery.getName());
        }
        // 排序
        if (pageQuery.getOrderBy() != null) {
            queryWrapper.orderBy(true, pageQuery.getIsASC(), pageQuery.getOrderBy());
        }

        IPage<Instock> iPage = instockMapper.selectPage(page, queryWrapper);
        log.debug("iPage:{}", iPage);
        return iPage;
    }

}
