package top.ericson.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ericson
 * @class BasePojo
 * @date 2020/03/31 16:40
 * @version 1.0
 * @description pojo的父类,实现了序列化接口,包含了创建时间,创建者,修改时间和修改者
 */
@Data
@Accessors(chain = true)
public class BasePojo implements Serializable {
    private static final long serialVersionUID = -357299869165981167L;
    private Date createTime;
    private Integer createUser;
    private Date updateTime;
    private Integer updateUser;
}
