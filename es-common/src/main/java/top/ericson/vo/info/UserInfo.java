package top.ericson.vo.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.ericson.pojo.User;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
public class UserInfo extends BaseInfo {

    private static final long serialVersionUID = 834948711849763496L;

    private Integer userId;

    private String username;

    private Integer roleId;

    private String rolename;

    private String salt;

    private String password;

    private String email;

    private String phone;

    private Integer valid;

    private String invitation;

    public UserInfo(User user) {
        userId = user.getUserId();
        roleId = user.getRoleId();
        username = user.getUsername();
        password = user.getPassword();
        email = user.getEmail();
        phone = user.getPhone();
        valid = user.getValid();
        invitation = user.getInvitation();
        updateTime = user.getUpdateTime();
        updateUserId = user.getUpdateUser();
        createTime = user.getCreateTime();
        createUserId = user.getCreateUser();
    }

    public static List<UserInfo> buildInfoList(List<User> userList, Map<Integer, String> usernameMap,
        Map<Integer, String> rolenameMap) {
        List<UserInfo> infoList = new ArrayList<UserInfo>();
        UserInfo info;
        for (User u : userList) {
            info = new UserInfo(u);
            info.setRolename(rolenameMap.get(info.getRoleId()))
                .setUpdateUserStr(usernameMap.get(info.getUpdateUserId()))
                .setCreateUserStr(usernameMap.get(info.getCreateUserId()));
            infoList.add(info);
        }
        return infoList;
    }

    public static String orderByCheak(String orderBy) {
        if (orderBy == null || "".equals(orderBy)) {
            return null;
        }
        switch (orderBy) {
            case "userId":
                return "user_id";
            case "roleId":
                return "role_id";
            case "username":
                return "username";
            case "email":
                return "email";
            case "phone":
                return "phone";
            case "valid":
                return "valid";
            case "invitation":
                return "invitation";
            case "updateTime":
                return "update_time";
            case "updateUserStr":
                return "update_user";
            case "createTime":
                return "create_time";
            case "createUserStr":
                return "create_user";
            default:
                return null;
        }
    }

    public User BuildPojo() {
        User user = new User();
        user.setRoleId(roleId)
            .setPassword(password)
            .setSalt(salt)
            .setUsername(username)
            .setEmail(email)
            .setPhone(phone)
            .setValid(valid)
            .setInvitation(invitation);
        return user;
    }

}