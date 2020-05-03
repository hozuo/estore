package top.ericson.vo.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.Cat;

/**
 * @author Ericson
 * @class CatInfo
 * @date 2020/04/14 15:02
 * @version 1.0
 * @description item传值对象
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CatInfo extends BaseInfo {

    private static final long serialVersionUID = -8172877679468537511L;

    private Integer catId;

    private Integer classId;

    private String catName;

    private String remark;

    /**
     * @author Ericson
     * @date 2020/05/03 23:34
     * @return
     * @description info构造pojo
     */
    public Cat buildPojo() {
        Cat cat = new Cat();
        cat.setCatId(catId)
            .setClassId(classId)
            .setCatName(catName)
            .setRemark(remark);
        return cat;
    }

    public CatInfo(Cat cat) {
        catId = cat.getCatId();
        classId = cat.getClassId();
        catName = cat.getCatName();
        remark = cat.getRemark();
        updateTime = cat.getUpdateTime();
        updateUserId = cat.getUpdateUser();
        createTime = cat.getCreateTime();
        createUserId = cat.getCreateUser();
    }

    /**
     * @date 2020/05/04
     * @author Ericson
     * @param @param cat
     * @param @param nameMap
     * @description 
     */
    public CatInfo(Cat cat, Map<String, String> usernameMap) {
        catId = cat.getCatId();
        classId = cat.getClassId();
        catName = cat.getCatName();
        remark = cat.getRemark();
        updateTime = cat.getUpdateTime();
        updateUserId = cat.getUpdateUser();
        updateUserStr = usernameMap.get(updateUserId.toString());
        createTime = cat.getCreateTime();
        createUserId = cat.getCreateUser();
        createUserStr = usernameMap.get(createUserId.toString());
    }
    
    /**
     * @author Ericson
     * @date 2020/05/04 01:01
     * @param catList
     * @param usernameMap
     * @return
     * @description 
     */
    public static List<CatInfo> buildInfoList(List<Cat> catList, Map<String, String> usernameMap) {
        List<CatInfo> infoList = new ArrayList<CatInfo>();
        CatInfo info;
        for (Cat cat : catList) {
            info = new CatInfo(cat);
            log.debug("info:{}", info);
            info.setUpdateUserStr(usernameMap.get(info.getUpdateUserId().toString()))
                .setCreateUserStr(usernameMap.get(info.getCreateUserId().toString()));
            infoList.add(info);
        }
        return infoList;
    }
    
    public static String orderByCheak(String orderBy) {
        if (orderBy == null || "".equals(orderBy)) {
            return null;
        }
        switch (orderBy) {
            case "catId":
                return "cat_id";
            case "catName":
                return "cat_name";
            case "remark":
                return "remark";
            case "updateUserStr":
                return "update_user";
            case "updateTime":
                return "update_time";
            case "createUserStr":
                return "create_user";
            case "createTime":
                return "create_time";
            default:
                return null;
        }
    }

}
