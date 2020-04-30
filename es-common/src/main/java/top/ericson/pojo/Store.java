package top.ericson.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Ericson
 * @class Store
 * @date 2020/04/16 18:33
 * @version 1.0
 * @description 仓库
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("es_store")
public class Store extends BasePojo{
    
    private static final long serialVersionUID = 8757962156769014452L;

    @TableId(type = IdType.AUTO)
    private Integer storeId;

    private String name;

    private String storeSn;

    private String address;

    private Integer manager;
    
}