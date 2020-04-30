package top.ericson.vo.info;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Ericson
 * @class ItemInfo
 * @date 2020/04/30
 * @version 1.0
 * @description 
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MenuInfo extends BaseInfo {

    private static final long serialVersionUID = -5189470101424289731L;

    private Integer menuId;

    private Integer parentId;

    private String menuname;

    private String url;

    private Integer type;

    private Integer sort;

    private String remark;

    private String permission;

}
