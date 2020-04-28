package top.ericson.vo;

import java.lang.reflect.Field;

import lombok.Data;

/**
* @author Ericson
* @class PagingQuery
* @date 2020/04/16 20:11
* @version 1.0
* @description 
*/
@Data
public class PageQuery {

    /**当前页码*/
    Integer pageCurrent = 1;
    /**页面行数*/
    Integer pageSize = 100;
    /**排序字段*/
    String orderBy;
    /**升序降序,默认升序*/
    Boolean isASC = true;
    /**按照名称查询*/
    String name;

    /**
     * @author Ericson
     * @date 2020/04/17
     * @param cls info类对象
     * @return true:合法
     * @description 校验pageQuery的合法性
     */
    public Boolean cheak(Class<?> cls) {
        if (pageCurrent < 1 || pageSize < 1) {
            return false;
        }
        if (orderBy == null) {
            return true;
        }
        // 检查name是否与字段名匹配
        Boolean flagOrderBy = false;
        Field[] declaredFields = cls.getDeclaredFields();
        for (Field f : declaredFields) {
            flagOrderBy |= orderBy.equals(f.getName());
        }
        return flagOrderBy;
    }
}
