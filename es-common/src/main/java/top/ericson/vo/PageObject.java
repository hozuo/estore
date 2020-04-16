package top.ericson.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ericson
 * @class PageObject
 * @date 2020/04/03 13:23
 * @version 1.0
 * @description 分页查询对象,封装了分页信息和当前页的数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageObject<T> implements Serializable {

    private static final long serialVersionUID = 400369567190610845L;

    /**当前页的页码值*/
    private Integer pageCurrent = 1;
    /**页面大小*/
    private Integer pageSize = 10;
    /**总行数(通过查询获得)*/
    private Integer rowCount = 100;
    /**总页数(通过计算获得)*/
    private Integer pageCount = 10;
    /**当前页记录*/
    private List<T> records;

}
