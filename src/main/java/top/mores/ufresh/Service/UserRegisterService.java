package top.mores.ufresh.Service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;
import top.mores.ufresh.POJO.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserRegisterService {

    /**
     * 注册增加用户
     *
     * @param userName 注册的用户名
     * @param password 注册的密码
     * @return 注册结果
     */
    public Map<Integer, String> addUser(String userName, String password) {
        Map<Integer, String> response = new HashMap<>();
        if (checkUser(userName)) {
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            User user = new User();
            user.setUser_name(userName);
            user.setPassword(password);
            if (userDao.addUser(user) == 1) {
                sqlSession.close();
                response.put(200, "注册成功");
            } else {
                sqlSession.close();
                response.put(500, "注册失败");
            }
        } else {
            response.put(404, "已存在该用户名");
        }
        return response;
    }

    /**
     * 检查是否已存在该用户名
     *
     * @param userName 传入需要检查的用户名
     * @return false:不存在；true:存在
     */
    public boolean checkUser(String userName) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        String name = userDao.checkUser(userName);
        return name == null;
    }
}
