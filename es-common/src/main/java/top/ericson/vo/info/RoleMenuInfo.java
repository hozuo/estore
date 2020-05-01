package top.ericson.vo.info;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Integer type;

    private List<RoleMenuInfo> children;

    public void add(RoleMenuInfo info) {
        children.add(info);
    }
}
