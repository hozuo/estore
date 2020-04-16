package top.ericson.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.ericson.pojo.Instock;
import top.ericson.mapper.InstockMapper;
import top.ericson.service.InstockService;
import top.ericson.vo.info.InstockInfo;

/**
 * @author Ericson
 * @class InstockServiceImpl
 * @date 2020/03/31 20:37
 * @version 1.0
 * @description 
 */
@Service
public class InstockServiceImpl implements InstockService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    InstockMapper instockMapper;

    /**
     * @author Ericson
     * @date 2020/03/31 20:40
     * @param page
     * @param rows
     * @return
     * @see top.ericson.service.InstockService#findInstockByPage(java.lang.Integer, java.lang.Integer)
     * @description 
     */
    @Override
    public List<Instock> findInstockByPage(Integer page, Integer rows, String orderBy, String orderType,
        List<Integer> inIdList, List<Integer> itemIdList) {
        int start = (page - 1) * rows;
        List<Instock> itemList = instockMapper.findInstockByPage(start, rows, orderBy, orderType, inIdList, itemIdList);
        return itemList;
    }

    /**
     * @author Ericson
     * @date 2020/04/05 00:45
     * @param inStockId
     * @return
     * @see top.ericson.service.InstockService#findInStockById(java.lang.String)
     * @description 
     */
    @Override
    public Instock findInstockById(String inStockId) {
        return instockMapper.selectById(inStockId);
    }

    /**
     * @author Ericson
     * @date 2020/04/08 19:07
     * @return Integer 总数
     * @see top.ericson.service.InstockService#selectCount()
     * @description 统计总数
     */
    @Override
    public Integer selectCount(String orderBy, String orderType, List<Integer> inIdList, List<Integer> itemIdList) {
        return instockMapper.selectCount(orderType, orderType, itemIdList, itemIdList);
    }

    /**
     * @author Ericson
     * @date 2020/04/08 20:55
     * @return
     * @see top.ericson.service.InstockService#selectCount()
     * @description 
     */
    @Override
    public Integer selectCount() {
        return instockMapper.selectCount();
    }

    /**
     * @author Ericson
     * @date 2020/04/08 23:26
     * @param inSn
     * @param storeId
     * @param userId
     * @param itemId
     * @param buyId
     * @param inState
     * @param num
     * @param inTime
     * @param stock
     * @param createTime
     * @param createUser
     * @param updateTime
     * @param updateUser
     * @see top.ericson.service.InstockService#createInstock(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Long, java.util.Date, java.lang.Long, java.util.Date, java.lang.Integer, java.util.Date, java.lang.Integer)
     * @description 使用pojo创建instock
     */
    @Override
    public Integer createInstock(Instock instock) {
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
     * @description 
     */
    @Override
    public Integer deleteInstockById(Integer inId) {
        return instockMapper.deleteById(inId);
    }

    /**
     * @author Ericson
     * @date 2020/04/14 18:41
     * @param instockInfo
     * @return
     * @see top.ericson.service.InstockService#createInstock(top.ericson.vo.info.InstockInfo)
     * @description 使用info创建instock
     */
    @Override
    public Integer createInstock(InstockInfo instockInfo) {
        Instock instock = new Instock(instockInfo);
        return this.createInstock(instock);
    }

    /**
     * @author Ericson
     * @date 2020/04/14 19:11
     * @param instockInfo
     * @return
     * @see top.ericson.service.InstockService#updateInstock(top.ericson.vo.info.InstockInfo)
     * @description 更新入库流水
     */
    @Override
    public Integer updateInstock(InstockInfo instockInfo) {
        Instock instock = new Instock(instockInfo);
        instock.setInId(instockInfo.getId())
            .setUpdateUser((Integer)request.getAttribute("userId"))
            .setUpdateTime(new Date());
        return instockMapper.updateById(instock);
    }

}
