package top.ericson.service.impl;

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

import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.Item;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.ItemInfo;
import top.ericson.mapper.ItemMapper;
import top.ericson.service.ItemService;

/**
 * @author Ericson
 * @class InstockServiceImpl
 * @date 2020/03/31 20:37
 * @version 1.0
 * @description 商品服务
 */
@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    ItemMapper itemMapper;

    /**
     * @author Ericson
     * @date 2020/04/16 20:07
     * @param start
     * @param rows
     * @param orderBy
     * @param orderType
     * @param name
     * @return
     * @see top.ericson.service.StoreService#findPage(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
     * @description 分页
     */
    @Override
    public IPage<Item> findPage(PageQuery pageQuery) {

        /*开启分页查询*/
        Page<Item> page = new Page<>(pageQuery.getPageCurrent(), pageQuery.getPageSize());
        log.debug("page:{}", page);

        /*条件构造器*/
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        // 名称查询
        if (pageQuery.getName() != null) {
            queryWrapper.like("name", pageQuery.getName());
        }
        // 排序
        if (pageQuery.getOrderBy() != null) {
            queryWrapper.orderBy(true, pageQuery.getIsASC(), pageQuery.getOrderBy());
        }

        IPage<Item> iPage = itemMapper.selectPage(page, queryWrapper);
        log.debug("iPage:{}", iPage);
        return iPage;
    }

    /**
     * @author Ericson
     * @date 2020/04/16 20:07
     * @param info
     * @return
     * @see top.ericson.service.StoreService#insert(top.ericson.vo.info.ItemInfo)
     * @description 增
     */
    @Override
    public Integer create(ItemInfo info) {
        Item item = info.buildPojo();
        return itemMapper.insert(item);
    }

    /**
     * @author Ericson
     * @date 2020/04/16 20:07
     * @param id
     * @return
     * @see top.ericson.service.StoreService#deleteById(java.lang.Integer)
     * @description 删
     */
    @Override
    public Integer deleteById(Integer id) {
        return itemMapper.deleteById(id);
    }

    /**
     * @author Ericson
     * @date 2020/04/16 20:07
     * @param itemInfo
     * @return
     * @see top.ericson.service.StoreService#update(top.ericson.vo.info.ItemInfo)
     * @description 改
     */
    @Override
    public Integer update(ItemInfo info) {
        return itemMapper.updateById(info.buildPojo());
    }

    /**
     * @author Ericson
     * @date 2020/04/16 20:07
     * @param id
     * @return
     * @see top.ericson.service.StoreService#findById(java.lang.Integer)
     * @description 查
     */
    @Override
    public Item findById(Integer id) {
        return itemMapper.selectById(id);
    }

    /**
     * @author Ericson
     * @date 2020/04/17 
     * @param idSet
     * @return nameMap
     * @see top.ericson.service.StoreService#findNamesById(java.lang.Integer)
     * @description 查很多名字
     */
    @Override
    public Map<Integer, String> findNamesById(Set<Integer> idSet) {
        List<Item> list = itemMapper.selectNamesById(idSet);
        Map<Integer, String> nameMap = new HashMap<Integer, String>();
        for (Item item : list) {
            if (item != null && item.getName() != null) {
                nameMap.put(item.getItemId(), item.getName());
            }
        }
        return nameMap;
    }

    /**
     * @author Ericson
     * @date 2020/04/17
     * @param id
     * @return
     * @see top.ericson.service.StoreService#findNameById(java.lang.Integer)
     * @description 查一个名字
     */
    @Override
    public String findNameById(Integer id) {
        return itemMapper.selectNameById(id);
    }

}
