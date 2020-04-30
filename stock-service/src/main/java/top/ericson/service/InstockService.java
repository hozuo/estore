package top.ericson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import top.ericson.pojo.Instock;
import top.ericson.vo.PageQuery;
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
     * @date 2020/04/05 00:45
     * @param inStockId
     * @return
     * @description 
     */
    Instock findById(String inStockId);

    /**
     * @author Ericson
     * @date 2020/04/08 19:08
     * @return
     * @description 统计总数
     */
    public Integer selectCount();

    /**
     * @author Ericson
     * @date 2020/04/09 00:46
     * @param inId
     * @description 
     */
    Integer deleteById(Integer inId);

    /**
     * @author Ericson
     * @date 2020/04/14 18:41
     * @param instockInfo
     * @return
     * @description 
     */
    Integer create(InstockInfo instockInfo);

    /**
     * @author Ericson
     * @date 2020/04/14 19:11
     * @param instockInfo
     * @return
     * @description 
     */
    Integer update(InstockInfo instockInfo);

    /**
     * @author Ericson
     * @date 2020/04/17 17:43
     * @param pageQuery
     * @return
     * @description 
     */
    IPage<Instock> findPage(PageQuery pageQuery);

    /**
     * @author Ericson
     * @date 2020/04/17 17:55
     * @param instock
     * @return
     * @description 
     */
    Integer create(Instock instock);
}
