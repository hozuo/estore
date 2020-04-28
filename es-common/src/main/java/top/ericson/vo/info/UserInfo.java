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

    private String password;

    private String email;

    private String phone;

    private String invitation;

    public UserInfo(User user) {
        userId = user.getUserId();
        username = user.getUsername();
        email = user.getEmail();
        phone = user.getPhone();
        invitation = user.getInvitation();
    }

    public static List<UserInfo> buildInfoList(List<User> userList, Map<Integer, String> usernameMap) {
        List<UserInfo> infoList = new ArrayList<UserInfo>();
        UserInfo info;
        for (User u : userList) {
            info = new UserInfo(u);
            info.setUpdateUserStr(usernameMap.get(info.getUpdateUserId()))
                .setCreateUserStr(usernameMap.get(info.getCreateUserId()));
            infoList.add(info);
        }
        return infoList;
    }

}