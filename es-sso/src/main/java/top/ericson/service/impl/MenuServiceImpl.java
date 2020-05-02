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

import lombok.extern.slf4j.Slf4j;
import top.ericson.mapper.MenuMapper;
import top.ericson.pojo.Menu;
import top.ericson.service.MenuService;
import top.ericson.vo.PageQuery;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    HttpServletRequest request;

    /**
     * @author Ericson
     * @date 2020/04/30
     * @param menu
     * @see top.ericson.service.MenuService#createMenu(top.ericson.vo.info.Menu)
     * @description 新建
     */
    @Override
    public void create(Menu menu) {
        Integer userId = (Integer)request.getAttribute("userId");
        log.debug("nemuId:{}", userId);
        Date now = new Date();
        menu.setCreateTime(now)
            .setCreateUser(userId)
            .setUpdateTime(now)
            .setUpdateUser(userId);
        menuMapper.insert(menu);
    }

    @Override
    public IPage<Menu> findPage(PageQuery pageQuery) {

        /*开启分页查询*/
        Page<Menu> page = new Page<>(pageQuery.getPageCurrent(), pageQuery.getPageSize());

        /*条件构造器*/
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();

        // 名称查询
        if (pageQuery.getName() != null) {
            queryWrapper.like("name", pageQuery.getName());
        }
        // 排序
        if (pageQuery.getOrderBy() != null && !"".equals(pageQuery.getOrderBy())) {
            queryWrapper.orderBy(true, pageQuery.getIsASC(), pageQuery.getOrderBy());
        }

        IPage<Menu> iPage = menuMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    /**
     * @author Ericson
     * @date 2020/04/29 15:00
     * @param id
     * @return
     * @see top.ericson.service.MenuService#deleteById(java.lang.Integer)
     * @description 
     */
    @Override
    public Integer deleteById(Integer id) {
        return menuMapper.deleteById(id);
    }

    /**
     * @author Ericson
     * @date 2020/04/30
     * @param id
     * @param nemu
     * @return
     * @see top.ericson.service.MenuService#updateById(java.lang.Integer, top.ericson.vo.info.Menu)
     * @description 
     */
    @Override
    public Integer updateById(Menu nemu) {
        nemu.setMenuId(nemu.getMenuId());
        Integer userId = (Integer)request.getAttribute("userId");
        nemu.setUpdateTime(new Date())
            .setUpdateUser(userId);
        return menuMapper.updateById(nemu);
    }

    /**
     * @author Ericson
     * @date 2020/05/01 17:42
     * @param idSet
     * @return
     * @see top.ericson.service.MenuService#findNamesById(java.util.Set)
     * @description 
     */
    @Override
    public Map<Integer, String> findNamesById(Set<Integer> idSet) {
        List<Menu> menuList = menuMapper.selectNamesById(idSet);
        HashMap<Integer, String> nameMap = new HashMap<Integer, String>();
        for (Menu menu : menuList) {
            nameMap.put(menu.getMenuId(), menu.getMenuName());
        }
        return nameMap;
    }

    /**
     * @author Ericson
     * @date 2020/05/01 17:59
     * @param idSet
     * @return
     * @see top.ericson.service.MenuService#findById(java.util.Set)
     * @description 
     */
    @Override
    public List<Menu> findByIds(Set<Integer> idSet) {
        List<Menu> menuList = menuMapper.selectByIds(idSet);
        return menuList;
    }

    @Override
    public Menu findById(Integer id) {
        return menuMapper.selectById(id);
    }

    /**
     * @author Ericson
     * @date 2020/05/01 23:18
     * @param parentId
     * @return
     * @see top.ericson.service.MenuService#findByParentId(java.lang.Integer)
     * @description 
     */
    @Override
    public List<Menu> findByParentId(Integer parentId) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        return menuMapper.selectList(wrapper);
    }

    /**
     * @author Ericson
     * @date 2020/05/02 19:52
     * @return
     * @see top.ericson.service.MenuService#findAll()
     * @description 
     */
    @Override
    public List<Menu> findAll() {
        return menuMapper.selectList(null);
    }

}
