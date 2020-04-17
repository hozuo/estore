package top.ericson.vo;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ericson
 * @class PageObject
 * @date 2020/04/03 13:23
 * @version 1.0
 * @description 分页查询对象,封装了分页信息和当前页的数据
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageObject<T> implements Serializable {

    private static final long serialVersionUID = 400369567190610845L;

    /**当前页的页码值*/
    private Long pageCurrent;
    /**页面大小*/
    private Long pageSize;
    /**总行数(通过查询获得)*/
    private Long total;
    /**总页数(通过计算获得)*/
    private Long pages;
    /**当前页记录*/
    private List<T> records;

    public PageObject(IPage<T> iPage, List<T> infoList) {
        pageCurrent = iPage.getCurrent();
        pageSize = iPage.getPages();
        total = iPage.getTotal();
        pages = iPage.getSize();
        records = infoList;
    }

}
