package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserManageService {

    /**
     * 获取全部用户信息
     *
     * @return 全部用户信息
     */
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
        session.close();
        return response;
    }

    /**
     * 根据用户名模糊查找用户
     *
     * @param username 用户名
     * @return 查找结果
     */
    public APIResponse<List<User>> selectUserData(String username) {

        try (SqlSession session = MybatisUtils.getSqlSession()) {
            UserDao userDao = session.getMapper(UserDao.class);
            List<User> userData = userDao.selectUser(username);
            if (!userData.isEmpty()) {
                return new APIResponse<>(200, "查询成功：共找到 " + userData.size() + " 条数据", userData);
            } else {
                return new APIResponse<>(404, "没有查询到使用类似用户名的用户", null);
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "查询失败：" + e.getMessage(), null);
        }
    }

    /**
     * 保存编辑的用户数据
     *
     * @param username      用户名
     * @param admin_enabled 是否是管理员
     * @param phone         手机号
     * @param email         邮箱
     * @param user_id       用户ID
     * @return 保存结果
     */
    public APIResponse<Void> saveEditUser(String username,
                                          boolean admin_enabled,
                                          String phone,
                                          String email,
                                          Integer user_id) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            UserDao userDao = session.getMapper(UserDao.class);
            int result = userDao.saveEditUserData(username, email, phone, admin_enabled, user_id);
            if (result == 1) {
                session.commit();
                return new APIResponse<>(200, "修改已保存");
            } else {
                session.rollback();
                return new APIResponse<>(500, "保存失败：服务器错误");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "保存失败" + e.getMessage());
        }
    }
}
