package top.ericson.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("es_supplier")
public class Supplier extends BasePojo {
    
    private static final long serialVersionUID = 6576795828361767182L;

    @TableId(type = IdType.AUTO)
    private Integer supplierId;

    private String name;

    private String address;

    private String manager;

    private String phone;

    private String fax;

    private String remark;
    
}