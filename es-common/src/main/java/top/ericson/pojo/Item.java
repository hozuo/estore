package top.ericson.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("es_item")
@NoArgsConstructor
public class Item extends BasePojo {

    private static final long serialVersionUID = -8628064781640712406L;

    @TableId(type = IdType.AUTO)
    private Integer itemId;

    private String name;

    private String itemSn;

    private Integer catId;

    private Integer specId;

    private Integer unitId;

    private Long buyPrice;

    private Long salePrice;

    private String remark;

}