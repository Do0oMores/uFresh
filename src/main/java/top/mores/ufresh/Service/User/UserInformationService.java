package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;
import top.mores.ufresh.POJO.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserInformationService {

    /**
     * 获取用户信息
     *
     * @param userID 传入用户ID
     * @return 用户信息
     */
    public Map<String, Object> getUserInformation(Integer userID) {
        Map<String, Object> response = new HashMap<>();

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User userData = userDao.returnUserData(userID);

        // 判断用户数据是否为空
        if (userData != null) {
            response.put("code", 200);
            response.put("msg", "成功获取用户信息");
            response.put("Data", userData);
        } else {
            response.put("code", 404);
            response.put("msg", "用户信息未找到");
        }

        // 关闭SqlSession
        sqlSession.close();
        return response;
    }

    /**
     * 保存用户信息
     * @param userName 用户名
     * @param password 密码
     * @param email 邮箱
     * @param userID 使用用户ID查询更新数据
     * @return 用户信息保存更新结果
     */
    public Map<Integer, String> saveUserInformation(String userName, String password, String email, Integer userID) {
        Map<Integer, String> response = new HashMap<>();

        try (SqlSession sqlSession = MybatisUtils.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);

            int result = userDao.saveUserData(userName, password, email, userID);
            if (result == 1) {
                sqlSession.commit();
                response.put(200, "个人信息已保存");
            } else {
                sqlSession.rollback();
                response.put(500, "个人信息保存失败");
            }
        } catch (Exception e) {
            response.put(500, "保存操作出现异常: " + e.getMessage());
        }

        return response;
    }
}
