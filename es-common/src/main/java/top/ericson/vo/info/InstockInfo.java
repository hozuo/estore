package top.ericson.vo.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.ericson.pojo.Instock;

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
public class InstockInfo extends BaseInfo {

    private static final long serialVersionUID = -4587826846673622150L;

    // 入库流水id
    private Integer id;
    // 入库流水编号
    private String sn;
    // 仓库id
    private Integer storeId;
    // 仓库名称
    private String storeStr;
    // 入库用户id
    private Integer userId;
    // 入库用户名称
    private String userStr;
    // 商品id
    private Integer itemId;
    // 商品名称
    private String itemStr;
    // 采购订单id
    private String buyId;
    // 流程状态
    private Integer inState;
    // 入库数量
    private Long num;
    // 入库时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inTime;
    // 实时库存(变动前)
    private Long stock;

    /**
     * @date 2020/04/14
     * @author Ericson
     * @param instock
     * @description 将pojo对象中的非ID变量赋给info
     */
    public InstockInfo(Instock instock) {
        id = instock.getInId();
        sn = instock.getInSn();
        storeId = instock.getStoreId();
        userId = instock.getUserId();
        itemId = instock.getItemId();
        buyId = instock.getBuyId();
        inState = instock.getInState();
        num = instock.getNum();
        inTime = instock.getInTime();
        stock = instock.getStock();
        updateTime = instock.getUpdateTime();
        updateUserId = instock.getUpdateUser();
        createTime = instock.getCreateTime();
        createUserId = instock.getCreateUser();
    }

    /**
     * @date 2020/04/14
     * @author Ericson
     * @param instock
     * @description 将pojo对象中的非ID变量赋给info
     */
    public InstockInfo(Instock instock, Map<String, String> usernameMap) {
        id = instock.getInId();
        sn = instock.getInSn();
        storeId = instock.getStoreId();
        userId = instock.getUserId();
        userStr = usernameMap.get(userId.toString());
        itemId = instock.getItemId();
        buyId = instock.getBuyId();
        inState = instock.getInState();
        num = instock.getNum();
        inTime = instock.getInTime();
        stock = instock.getStock();
        updateTime = instock.getUpdateTime();
        updateUserId = instock.getUpdateUser();
        updateUserStr = usernameMap.get(updateUserId.toString());
        createTime = instock.getCreateTime();
        createUserId = instock.getCreateUser();
        createUserStr = usernameMap.get(createUserId.toString());
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
        boolean cheakNullflag = this.getSn() != null && this.getStoreId() != null && this.getUserId() != null
            && this.getItemId() != null && this.getBuyId() != null && this.getInState() != null && this.getNum() != null
            && this.getInTime() != null && this.getStock() != null;
        return cheakNullflag;
    }

    public static List<InstockInfo> buildInfoList(List<Instock> instockList, Map<String, String> usernameMap,
        Map<String, String> itemsNameMap, Map<String, String> storesNameMap) {
        List<InstockInfo> infoList = new ArrayList<InstockInfo>();
        InstockInfo info;
        for (Instock i : instockList) {
            info = new InstockInfo(i);
            info.setUserStr(usernameMap.get(info.getUserId()
                .toString()))
                .setItemStr(itemsNameMap.get(info.getItemId()
                    .toString()))
                .setStoreStr(storesNameMap.get(info.getStoreId()
                    .toString()))
                .setUpdateUserStr(usernameMap.get(info.getUpdateUserId()
                    .toString()))
                .setCreateUserStr(usernameMap.get(info.getCreateUserId()
                    .toString()));
            infoList.add(info);
        }
        return infoList;
    }
}
