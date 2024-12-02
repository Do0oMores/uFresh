package top.mores.ufresh.Service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserLoginService {

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return Map验证结果
     */
    public Map<Integer, String> loginVerify(String username, String password) {
        Map<Integer, String> responseData = new HashMap<>();
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        String userData = userDao.getUserPassword(username);
        if (userData == null) {
            responseData.put(404, "未找到您的账号，请先注册！");
            sqlSession.close();
            return responseData;
        } else {
            if (password.equals(userData)) {
                sqlSession.close();
                responseData.put(200, "登录成功");
            }
        }
        return responseData;
    }
}
