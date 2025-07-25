package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.User;

import java.util.List;

@Service
public class UserManageService {

    /**
     * 获取全部用户信息
     *
     * @return 全部用户信息
     */
    public APIResponse<List<User>> getUsers() {
        SqlSession session = MybatisUtils.getSqlSession();
        UserDao userDao = session.getMapper(UserDao.class);
        try {
            List<User> usersData = userDao.fetchUsers();
            return new APIResponse<>(200, usersData);
        } catch (Exception e) {
            return new APIResponse<>(500, "拉取用户数据失败" + e.getMessage());
        }
    }

    /**
     * 传入用户的用户名，邮箱，电话，注册时间进行查询
     *
     * @param user 传入的用户数据对象
     * @return 查询结果
     */
    public APIResponse<List<User>> selectUserData(User user) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            UserDao userDao = session.getMapper(UserDao.class);
            List<User> userData = userDao.selectUser(user);
            if (!userData.isEmpty()) {
                return new APIResponse<>(200, "共找到 " + userData.size() + " 条数据", userData);
            } else {
                return new APIResponse<>(404, "无结果", null);
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
