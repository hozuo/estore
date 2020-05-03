package top.ericson.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ericson
 * @class Cat
 * @date 2020/05/03 23:27
 * @version 1.0
 * @description category商品类别
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("es_cat")
@NoArgsConstructor
public class Cat extends BasePojo {

    private static final long serialVersionUID = -2995228831623892800L;

    @TableId(type = IdType.AUTO)
    private Integer catId;

    private Integer classId;

    private String catName;

    private String remark;


}