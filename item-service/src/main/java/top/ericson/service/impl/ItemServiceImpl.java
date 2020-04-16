package top.ericson.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.ericson.exception.ServiceException;
import top.ericson.pojo.Item;
import top.ericson.vo.PageObject;
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
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    ItemMapper itemMapper;

    /**
     * @author Ericson
     * @date 2020/03/31 20:40
     * @param startIndex 起始下标
     * @param pageSize 每页的行数,也是查询的个数
     * @param name 查询的名称,可以为""
     * @return PageObject<Item> 
     * @see top.ericson.service.InStockService#findInstockByPage(java.lang.Integer, java.lang.Integer)
     * @description 查询一页商品
     */
    @Override
    public PageObject<Item> findItemsByPage(Integer pageCurrent, Integer pageSize, String name) {
        System.out.println(pageCurrent.intValue());
        System.out.println(pageSize.intValue());
        System.out.println(name);
        // 1.验证参数合法性
        // 验证pageCurrent的合法性，
        if (pageCurrent == null || pageCurrent < 1) {
            // 不合法抛出IllegalArgumentException异常
            throw new IllegalArgumentException("当前页码不正确");
        }
        // 2.基于条件查询总记录数
        // 执行查询
        int rowCount = itemMapper.getRowCount(name);
        // 验证查询结果，假如结果为0不再执行如下操作
        if (rowCount == 0) {
            throw new ServiceException("系统没有查到对应记录", 0);
        }
        // 3.基于条件查询当前页记录(pageSize定义为2)
        // 计算startIndex
        int startIndex = (pageCurrent - 1) * pageSize;
        // 执行当前数据的查询操作
        List<Item> records = itemMapper.findPageObjects(startIndex, pageSize, name);
        // 4.对分页信息以及当前页记录进行封装
        // 构建PageObject对象
        PageObject<Item> pageObject = new PageObject<>();
        // 封装数据
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setPageSize(pageSize);
        pageObject.setRowCount(rowCount);
        pageObject.setRecords(records);
        pageObject.setPageCount((rowCount - 1) / pageSize + 1);
        // 5.返回封装结果。
        return pageObject;
    }

    /**
     * @author Ericson
     * @date 2020/04/05 01:05
     * @param ItemId
     * @return
     * @see top.ericson.service.ItemService#findItemById(java.lang.Integer)
     * @description 
     */
    @Override
    public Item findItemById(Integer ItemId) {
        return itemMapper.selectById(ItemId);
    }

    @Override
    public Integer insertItem(ItemInfo itemInfo) {
        Item item = new Item(itemInfo);
        Date now = new Date();
        item.setCreateTime(now)
            .setCreateUser((Integer)request.getAttribute("userId"))
            .setUpdateTime(now)
            .setUpdateUser((Integer)request.getAttribute("userId"));
        return itemMapper.insert(item);
    }

    /**
     * @author Ericson
     * @date 2020/04/14 15:57
     * @param itemId
     * @return
     * @see top.ericson.service.ItemService#deleteItemById(java.lang.Integer)
     * @description 
     */
    @Override
    public Integer deleteItemById(Integer id) {
        return itemMapper.deleteById(id);
    }

    /**
     * @author Ericson
     * @date 2020/04/14 16:07
     * @param itemInfo
     * @return 
     * @see top.ericson.service.ItemService#updateItem(top.ericson.vo.info.ItemInfo)
     * @description 
     */
    @Override
    public Integer updateItem(ItemInfo itemInfo) {
        Item item = new Item(itemInfo);
        Date date = new Date();
        item.setUpdateTime(date)
            .setUpdateUser((Integer)request.getAttribute("userId"));
        return itemMapper.updateById(item);
    }

}
