package top.mores.ufresh.Service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return Map验证结果
     */
    public Map<String, Object> loginVerify(String username, String password) {
        Map<String, Object> responseData = new HashMap<>();
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        String userPassword = userDao.getUserPassword(username);
        Integer userID = userDao.getUserID(username);

        if (userPassword == null) {
            responseData.put("code", 404);
            responseData.put("msg", "未找到您的账号，请先注册！");
            sqlSession.close();
            return responseData;
        } else {
            if (password.equals(userPassword)) {
                sqlSession.close();
                responseData.put("code", 200);
                responseData.put("msg", "登录成功");
                responseData.put("userID", userID);
                responseData.put("role", checkAdmin(username));
            } else {
                sqlSession.close();
                responseData.put("code", 401);
                responseData.put("msg", "密码错误");
            }
        }
        return responseData;
    }

    /**
     * 查询用户角色
     *
     * @param username 用户名
     * @return 用户角色
     */
    public String checkAdmin(String username) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        //查询数据库中用户角色
        int enabled = userDao.checkRole(username);
        sqlSession.close();
        //返回用户角色：管理员或用户
        if (enabled == 1) {
            return "admin";
        } else {
            return "user";
        }
    }
}
