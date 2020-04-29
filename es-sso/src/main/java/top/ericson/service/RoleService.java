package top.ericson.service;

import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.core.metadata.IPage;

import top.ericson.pojo.Role;
import top.ericson.vo.PageQuery;
import top.ericson.vo.info.RoleInfo;

/**
 * @author Ericson
 * @class RoleService
 * @date 2020/04/29 16:06
 * @version 1.0
 * @description role服务
 */
public interface RoleService {


    /**
     * @author Ericson
     * @date 2020/04/29 18:45
     * @param roleInfo
     * @description 增
     */
    void create(RoleInfo roleInfo);
    
    /**
     * @author Ericson
     * @date 2020/04/29 18:45
     * @param id
     * @return
     * @description 删
     */
    Integer deleteById(Integer id);
    
    /**
     * @author Ericson
     * @date 2020/04/29 18:46
     * @param roleInfo
     * @return
     * @description 改
     */
    Integer updateById(RoleInfo roleInfo);

    /**
     * @author Ericson
     * @date 2020/04/29 18:46
     * @param pageQuery
     * @return
     * @description 分页查询
     */
    IPage<Role> findPage(PageQuery pageQuery);

    /**
     * @author Ericson
     * @date 2020/04/29 18:46
     * @param roleId
     * @return
     * @description 用id找名字
     */
    String findNameById(Integer roleId);

    /**
     * @author Ericson
     * @date 2020/04/29 18:46
     * @param idSet
     * @return 
     * @description 用idSet找很多名字
     */
    Map<Integer, String> findNamesById(Set<Integer> idSet);

}
