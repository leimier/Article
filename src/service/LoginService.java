package service;

import bean.User;
import util.DataBaseUtils;

/**
 * Created by Dragon on 2016/11/28.
 * 登陆服务类
 */
public class LoginService {
    public User getUser(String username){
        String sql = "select * from t_user where username=?";
        User user = DataBaseUtils.queryForBean(sql, User.class, username);
        if (user == null){
            return null;
        }
        System.out.print(user);
        return user;
    }
}
