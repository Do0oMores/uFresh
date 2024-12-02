package top.mores.ufresh.Service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;
import top.mores.ufresh.POJO.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterService {

    /**
     * 注册增加用户
     *
     * @param userName 注册的用户名
     * @param password 注册的密码
     * @param phone 注册手机号
     * @return 注册结果
     */
    public Map<Integer, String> addUser(String userName, String password,String phone) {
        Map<Integer, String> response = new HashMap<>();

        if (checkUser(userName)) { // 检查用户名是否已存在
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            UserDao userDao = sqlSession.getMapper(UserDao.class);

            User user = new User();

            LocalDateTime currentTime = LocalDateTime.now();
            user.setUser_name(userName);
            user.setPassword(password);
            user.setRegister_time(currentTime);// 设置注册时间为当前时间
            user.setPhone(phone);

            if (userDao.addUser(user) == 1) {
                //提交事务
                sqlSession.commit();
                response.put(200, "注册成功");
            } else {
                response.put(500, "注册失败");
            }
            sqlSession.close();
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
