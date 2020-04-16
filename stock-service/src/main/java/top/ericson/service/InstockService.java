package top.ericson.service;

import java.util.List;

import top.ericson.pojo.Instock;
import top.ericson.vo.info.InstockInfo;

/**
 * @author Ericson
 * @class InstockService
 * @date 2020/03/31 17:36
 * @version 1.0
 * @description 入库service
 */
public interface InstockService {

    /**
     * @author Ericson
     * @date 2020/03/31 20:39
     * @param page
     * @param rows
     * @return
     * @description 
     */
    List<Instock> findInstockByPage(Integer page, Integer rows, String orderBy, String orderType,
        List<Integer> inIdList, List<Integer> itemIdList);

    /**
     * @author Ericson
     * @date 2020/04/05 00:45
     * @param inStockId
     * @return
     * @description 
     */
    Instock findInstockById(String inStockId);

    /**
     * @author Ericson
     * @date 2020/04/08 19:08
     * @return
     * @description 统计总数
     */
    public Integer selectCount();

    /**
     * @author Ericson
     * @date 2020/04/08 20:54
     * @param orderBy
     * @param orderType
     * @param inIdList
     * @param itemIdList
     * @return
     * @description 
     */
    Integer selectCount(String orderBy, String orderType, List<Integer> inIdList, List<Integer> itemIdList);

    /**
     * @author Ericson
     * @date 2020/04/08 21:53
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
     * @description 
     */
    Integer createInstock(Instock instock);

    /**
     * @author Ericson
     * @date 2020/04/09 00:46
     * @param inId
     * @description 
     */
    Integer deleteInstockById(Integer inId);

    /**
     * @author Ericson
     * @date 2020/04/14 18:41
     * @param instockInfo
     * @return
     * @description 
     */
    Integer createInstock(InstockInfo instockInfo);

    /**
     * @author Ericson
     * @date 2020/04/14 19:11
     * @param instockInfo
     * @return
     * @description 
     */
    Integer updateInstock(InstockInfo instockInfo);
}
