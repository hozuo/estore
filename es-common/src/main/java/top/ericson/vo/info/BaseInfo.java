package top.ericson.vo.info;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ericson
 * @class BasePojo
 * @date 2020/03/31 16:40
 * @version 1.0
 * @description info的父类,实现了序列化接口,将用户变为姓名
 */
@Data
@Accessors(chain = true)
public class BaseInfo implements Serializable {
    
    private static final long serialVersionUID = 3391449269368088291L;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createTime;
    
    protected Integer createUserId;
    protected String createUserStr;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date updateTime;
    
    protected Integer updateUserId;
    protected String updateUserStr;
}
