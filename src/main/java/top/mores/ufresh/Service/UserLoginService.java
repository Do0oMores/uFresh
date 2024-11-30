package top.mores.ufresh.Service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;
import top.mores.ufresh.POJO.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserLoginService {

    public Map<Integer, String> loginVerify(String username, String password) {
        Map<Integer, String> response = new HashMap<>();
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User userData = userDao.getUserPassword(username);
        if (userData == null) {
            response.put(404, "未找到您的账号，请先注册！");
            return response;
        } else {
            String passwordData = userData.getPassword();
            if (password.equals(passwordData)) {
                response.put(200, "OK");
            }
        }
        return response;
    }
}
