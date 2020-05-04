package top.ericson.vo.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.ericson.pojo.Store;

/**
 * @author Ericson
 * @class Store
 * @date 2020/04/16 18:33
 * @version 1.0
 * @description 仓库info
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
public class StoreInfo extends BaseInfo {

    private static final long serialVersionUID = 6925026774463466605L;

    private Integer storeId;

    private String storeName;

    private String sn;

    private String addr;

    private Integer managerId;

    private String managerStr;

    /**
     * @date 2020/04/17
     * @author Ericson
     * @param store
     * @description 将pojo对象中的非ID变量赋给info
     */
    public StoreInfo(Store store) {
        storeId = store.getStoreId();
        storeName = store.getStoreName();
        sn = store.getStoreSn();
        addr = store.getAddress();
        managerId = store.getManager();
        updateTime = store.getUpdateTime();
        updateUserId = store.getUpdateUser();
        createTime = store.getCreateTime();
        createUserId = store.getCreateUser();
    }

    public StoreInfo(Store store, Map<String, String> usernameMap) {
        storeId = store.getStoreId();
        storeName = store.getStoreName();
        sn = store.getStoreSn();
        addr = store.getAddress();
        managerId = store.getManager();
        managerStr = usernameMap.get(managerId.toString());
        updateTime = store.getUpdateTime();
        updateUserId = store.getUpdateUser();
        updateUserStr = usernameMap.get(updateUserId.toString());
        createTime = store.getCreateTime();
        createUserId = store.getCreateUser();
        createUserStr = usernameMap.get(createUserId.toString());
    }

    /**
     * @author Ericson
     * @date 2020/04/17 10:14
     * @return true代表非空可用
     * @description 判断主要参数是否非空
     */
    public boolean cheak() {
        boolean cheakNullflag = (storeName != null && sn != null && addr != null && managerId != null);
        return cheakNullflag;
    }

    public Store buildPojo() {
        Store store = new Store();
        store.setStoreId(storeId)
            .setStoreName(storeName)
            .setStoreSn(sn)
            .setAddress(addr)
            .setManager(managerId);
        return store;
    }

    /**
     * @author Ericson
     * @date 2020/04/17 12:25
     * @param storeList
     * @param usernameMap
     * @return
     * @description 通过storelist和用户名称map构造infolist
     */
    public static List<StoreInfo> buildInfoList(List<Store> storeList, Map<String, String> usernameMap) {
        List<StoreInfo> infoList = new ArrayList<StoreInfo>();
        StoreInfo storeInfo;
        for (Store s : storeList) {
            storeInfo = new StoreInfo(s);
            storeInfo.setManagerStr(usernameMap.get(storeInfo.getManagerId()
                .toString()))
                .setUpdateUserStr(usernameMap.get(storeInfo.getUpdateUserId()
                    .toString()))
                .setCreateUserStr(usernameMap.get(storeInfo.getCreateUserId()
                    .toString()));
            infoList.add(storeInfo);
        }
        return infoList;
    }

}