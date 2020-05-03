package top.ericson.vo.info;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.ericson.pojo.Menu;

/**
 * @author Ericson
 * @class ItemInfo
 * @date 2020/05/01
 * @version 1.0
 * @description 角色对应的菜单,包含级别
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuInfo {

    private Integer menuId;

    private Integer parentId;

    private String menuName;

    private String url;

    private Integer type;

    private Integer sort;

    private List<RoleMenuInfo> children;

    public void add(RoleMenuInfo info) {
        children.add(info);
    }

    public RoleMenuInfo(Menu menu) {
        menuId = menu.getMenuId();
        parentId = menu.getParentId();
        menuName = menu.getMenuName();
        url = menu.getUrl();
        type = menu.getType();
        sort = menu.getSort();
        children = new ArrayList<RoleMenuInfo>();
    }
}
