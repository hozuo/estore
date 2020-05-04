package top.ericson.vo;

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
    Integer pageSize = 100000;
    /**排序字段*/
    String orderBy;
    /**升序降序,默认升序*/
    Boolean isASC = true;
    /**按照名称查询*/
    String name;

}
