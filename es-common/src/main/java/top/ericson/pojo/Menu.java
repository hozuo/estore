package top.ericson.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_menu")
@NoArgsConstructor
public class Menu extends BasePojo {

    private static final long serialVersionUID = -1385098944545499090L;

    @TableId(type = IdType.AUTO)
    private Integer menuId;

    private Integer parentId;

    private String menuName;

    private String url;

    private Integer type;

    private Integer sort;

    private String remark;

    private String permission;

}