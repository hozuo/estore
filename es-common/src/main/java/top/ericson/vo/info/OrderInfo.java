package top.ericson.vo.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.ericson.pojo.Order;

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
public class OrderInfo extends BaseInfo {

    private static final long serialVersionUID = 2040676101069409405L;

    private String id;

    private String sn;

    private Integer supplierId;

    private String supplierStr;

    private Integer userId;

    private String userStr;

    private Integer deptId;

    private String deptStr;

    private Integer storeId;

    private String storeStr;

    private Integer state;
    
//    private List<OrderItemKeyInfo> orderItems;

    /**
     * @date 2020/04/20
     * @author Ericson
     * @param order
     * @description 将pojo对象中的变量赋给info
     */
    public OrderInfo(Order order) {
        id = order.getOrderId();
        sn = order.getOrderSn();
        supplierId = order.getSupplierId();
        userId = order.getUserId();
        deptId = order.getDeptId();
        storeId = order.getStoreId();
        state = order.getState();

        updateTime = order.getUpdateTime();
        updateUserId = order.getUpdateUser();
        createTime = order.getCreateTime();
        createUserId = order.getCreateUser();
    }

    /**
     * @date 2020/04/20
     * @author Ericson
     * @param instock
     * @description 将pojo对象中的变量赋给info
     */
    public OrderInfo(Order order, Map<String, String> usernameMap) {
        id = order.getOrderId();
        sn = order.getOrderSn();
        supplierId = order.getSupplierId();
        userId = order.getUserId();
        userStr = usernameMap.get(userId.toString());
        deptId = order.getDeptId();
        storeId = order.getStoreId();
        state = order.getState();

        updateTime = order.getUpdateTime();
        updateUserId = order.getUpdateUser();
        updateUserStr = usernameMap.get(updateUserId.toString());
        createTime = order.getCreateTime();
        createUserId = order.getCreateUser();
        createUserStr = usernameMap.get(createUserId.toString());
    }

    /**
     * @author Ericson
     * @date 2020/04/20 16:07
     * @return
     * @description 
     */
    public Order buildPojo() {
        Order order = new Order();
        order.setOrderId(id)
            .setOrderSn(sn)
            .setSupplierId(supplierId)
            .setUserId(userId)
            .setDeptId(deptId)
            .setStoreId(storeId)
            .setState(state);
        return order;
    }

    /**
     * @author Ericson
     * @date 2020/04/14 17:37
     * @param this
     * @return true代表非空可用
     * @description 判断主要参数是否非空
     */
    public boolean cheak() {
        // true:任意对象为null
        boolean cheakNullflag =
            sn != null && supplierId != null && userId != null && deptId != null && storeId != null && state != null;
        return cheakNullflag;
    }

    /**
     * @author Ericson
     * @date 2020/04/20 15:09
     * @param orderList
     * @param usernameMap
     * @return List<OrderInfo>
     * @description 很多构造函数
     */
    public static List<OrderInfo> buildInfoList(List<Order> orderList, Map<String, String> supplierNameMap,
        Map<String, String> usernameMap, Map<String, String> deptNameMap, Map<String, String> storeNameMap) {
        if (orderList == null) {
            return null;
        }
        List<OrderInfo> infoList = new ArrayList<OrderInfo>();
        OrderInfo info;
        for (Order s : orderList) {
            info = new OrderInfo(s);
            if (supplierNameMap != null) {
                info.setSupplierStr(supplierNameMap.get(info.getSupplierId()
                    .toString()));
            }
            if (usernameMap != null) {
                info.setUserStr(usernameMap.get(info.getUserId()
                    .toString()))
                    .setUpdateUserStr(usernameMap.get(info.getUpdateUserId()
                        .toString()))
                    .setCreateUserStr(usernameMap.get(info.getCreateUserId()
                        .toString()));
            }
            if (deptNameMap != null) {
                // TODO
                info.setDeptStr(deptNameMap.get(info.getDeptId()
                    .toString()));
            }
            if (storeNameMap != null) {
                info.setStoreStr(storeNameMap.get(info.getStoreId()
                    .toString()));
            }
            infoList.add(info);
        }
        return infoList;
    }

}
