package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;
import top.mores.ufresh.POJO.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserManageService {

    public Map<String, Object> getUsers() {
        Map<String, Object> response = new HashMap<>();
        SqlSession session = MybatisUtils.getSqlSession();
        UserDao userDao = session.getMapper(UserDao.class);
        try {
            List<User> usersData = userDao.fetchUsers();
            response.put("code", 200);
            response.put("Data", usersData);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "拉取用户信息失败：" + e.getMessage());
        }
        return response;
    }
}
