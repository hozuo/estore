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
@TableName("sys_user")
public class User extends BasePojo {

    private static final long serialVersionUID = 6543481801897172072L;

    @TableId(type = IdType.AUTO)
    private Integer userId;

    private Integer empId;

    private Integer roleId;

    private String username;

    private String password;

    private String salt;

    private String email;

    private String phone;

    private Integer valid;

    private String invitation;
}