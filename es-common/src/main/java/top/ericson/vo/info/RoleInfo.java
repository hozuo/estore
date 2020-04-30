package top.ericson.vo.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.ericson.pojo.Role;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
public class RoleInfo extends BaseInfo {

    private static final long serialVersionUID = -6046234171635587338L;

    private Integer roleId;

    private String rolename;

    private String remark;

    public RoleInfo(Role role) {
        roleId = role.getRoleId();
        rolename = role.getRolename();
        remark = role.getRemark();
        updateTime = role.getUpdateTime();
        updateUserId = role.getUpdateUser();
        createTime = role.getCreateTime();
        createUserId = role.getCreateUser();
    }

    public static String orderByCheak(String orderBy) {
        if (orderBy == null || "".equals(orderBy)) {
            return null;
        }
        switch (orderBy) {
            case "roleId":
                return "role_id";
            case "name":
                return "name";
            case "remark":
                return "remark";
            default:
                return null;
        }
    }

    public Role BuildPojo() {
        Role role = new Role();
        role.setRoleId(roleId)
            .setRolename(rolename)
            .setRemark(remark);
        return role;
    }

    /**
     * @author Ericson
     * @date 2020/04/29 20:37
     * @param roleList
     * @param usernameMap
     * @param rolenameMap
     * @return
     * @description
     */
    public static List<RoleInfo> buildInfoList(List<Role> roleList, Map<Integer, String> usernameMap) {
        List<RoleInfo> infoList = new ArrayList<RoleInfo>();
        RoleInfo roleInfo;
        for (Role s : roleList) {
            roleInfo = new RoleInfo(s);
            roleInfo.setUpdateUserStr(usernameMap.get(roleInfo.getUpdateUserId()))
                .setCreateUserStr(usernameMap.get(roleInfo.getCreateUserId()));
            infoList.add(roleInfo);
        }
        return infoList;
    }

}