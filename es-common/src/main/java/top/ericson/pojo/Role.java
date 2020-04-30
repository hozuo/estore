package top.ericson.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
public class Role extends BasePojo {

    private static final long serialVersionUID = 6084123376730401498L;

    @TableId(type = IdType.AUTO)
    private Integer roleId;

    private String rolename;

    private String remark;
    
}