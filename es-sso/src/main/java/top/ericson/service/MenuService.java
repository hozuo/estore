package top.ericson.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.core.metadata.IPage;

import top.ericson.pojo.Menu;
import top.ericson.vo.PageQuery;

/**
 * @author Ericson
 * @class MenuService
 * @date 2020/04/30 23:35
 * @version 1.0
 * @description
 */
public interface MenuService {

    void create(Menu role);
    
    Integer deleteById(Integer id);
    
    Integer updateById(Menu role);

    IPage<Menu> findPage(PageQuery pageQuery);
    
    /**
     * @author Ericson
     * @date 2020/05/01
     * @param idSet
     * @description 
     */
    Map<Integer, String> findNamesById(Set<Integer> idSet);
    
    List<Menu> findByIds(Set<Integer> idSet);

    Menu findById(Integer id);

    /**
     * @author Ericson
     * @date 2020/05/01 23:18
     * @param parentId
     * @description 
     */
    List<Menu> findByParentId(Integer parentId);
    
}
