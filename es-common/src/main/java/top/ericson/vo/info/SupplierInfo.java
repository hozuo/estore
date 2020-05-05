package top.ericson.vo.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.ericson.pojo.Supplier;

/**
 * @author Ericson
 * @class Instock
 * @date 2020/03/31 16:38
 * @version 1.0
 * @description 入库订单实体,对应es_instock表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
public class SupplierInfo extends BaseInfo {

    private static final long serialVersionUID = -2864536785247376458L;

    private Integer supId;

    private String name;

    private String address;

    private String manager;

    private String phone;

    private String fax;

    private String remark;

    /**
     * @date 2020/04/20
     * @author Ericson
     * @param supplier
     * @description 将pojo对象中的变量赋给info
     */
    public SupplierInfo(Supplier supplier) {
        supId = supplier.getSupplierId();
        name = supplier.getName();
        address = supplier.getAddress();
        manager = supplier.getManager();
        phone = supplier.getPhone();
        fax = supplier.getFax();
        remark = supplier.getRemark();

        updateTime = supplier.getUpdateTime();
        updateUserId = supplier.getUpdateUser();
        createTime = supplier.getCreateTime();
        createUserId = supplier.getCreateUser();
    }

    /**
     * @date 2020/04/20
     * @author Ericson
     * @param instock
     * @description 将pojo对象中的变量赋给info
     */
    public SupplierInfo(Supplier supplier, Map<String, String> usernameMap) {
        supId = supplier.getSupplierId();
        name = supplier.getName();
        address = supplier.getAddress();
        manager = supplier.getManager();
        phone = supplier.getPhone();
        fax = supplier.getFax();
        remark = supplier.getRemark();
        updateTime = supplier.getUpdateTime();
        updateUserId = supplier.getUpdateUser();
        updateUserStr = usernameMap.get(updateUserId.toString());
        createTime = supplier.getCreateTime();
        createUserId = supplier.getCreateUser();
        createUserStr = usernameMap.get(createUserId.toString());
    }

    /**
     * @author Ericson
     * @date 2020/04/20 16:07
     * @return
     * @description
     */
    public Supplier buildPojo() {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(supId)
            .setName(name)
            .setAddress(address)
            .setManager(manager)
            .setPhone(phone)
            .setFax(fax)
            .setRemark(remark);
        return supplier;
    }

    /**
     * @author Ericson
     * @date 2020/04/20 15:09
     * @param supplierList
     * @param usernameMap
     * @return List<SupplierInfo>
     * @description 很多构造函数
     */
    public static List<SupplierInfo> buildInfoList(List<Supplier> supplierList, Map<String, String> usernameMap) {
        List<SupplierInfo> infoList = new ArrayList<SupplierInfo>();
        SupplierInfo info;
        for (Supplier s : supplierList) {
            info = new SupplierInfo(s);
            info.setUpdateUserStr(usernameMap.get(info.getUpdateUserId()
                .toString()))
                .setCreateUserStr(usernameMap.get(info.getCreateUserId()
                    .toString()));
            infoList.add(info);
        }
        return infoList;
    }

}
