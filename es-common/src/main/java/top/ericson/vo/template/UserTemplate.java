package top.ericson.vo.template;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ericson
 * @class UserTemplate
 * @date 2020/04/09 21:49
 * @version 1.0
 * @description user数据模板,用于前端传值
 */
@Data
@Accessors(chain = true)
public class UserTemplate {

    private String username;

    private String password;

    private String email;

    private String phone;
    
    private String invitation;

}