package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.User;

@Service
public class UserInformationService {

    /**
     * 获取用户信息
     *
     * @param userID 传入用户ID
     * @return 用户信息
     */
    public APIResponse<User> getUserInformation(Integer userID) {
        try (SqlSession sqlSession = MybatisUtils.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            User userData = userDao.returnUserData(userID);
            if (userData != null) {
                return new APIResponse<>(200, "成功获取用户信息", userData);
            } else {
                return new APIResponse<>(500, "用户信息未找到");
            }
        }
    }

    /**
     * 保存用户信息
     *
     * @param userName 用户名
     * @param password 密码
     * @param email    邮箱
     * @param userID   使用用户ID查询更新数据
     * @return 用户信息保存更新结果
     */
    public APIResponse<Void> saveUserInformation(String userName, String password, String email, Integer userID) {
        try (SqlSession sqlSession = MybatisUtils.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            int result = userDao.saveUserData(userName, password, email, userID);
            if (result == 1) {
                sqlSession.commit();
                return new APIResponse<>(200, "个人信息已保存");
            } else {
                sqlSession.rollback();
                return new APIResponse<>(401, "个人信息保存失败");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "保存操作出现异常：" + e.getMessage());
        }
    }

    /**
     * 将头像url保存到数据库
     *
     * @param userID    用户ID
     * @param avatarUrl 头像URL
     * @return 保存结果
     */
    public boolean saveAvatarUrl(Integer userID, String avatarUrl) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        if (userDao.saveUserAvatar(avatarUrl, userID) == 1) {
            sqlSession.commit();
            return true;
        } else {
            sqlSession.rollback();
            return false;
        }
    }

    /**
     * 保存配送信息
     *
     * @param address 地址
     * @param phone   电话
     * @param userID  保存该用户ID的信息
     * @return 保存操作执行结果
     */
    public APIResponse<Void> saveShippingInformation(String address, String phone, Integer userID) {
        try (SqlSession sqlSession = MybatisUtils.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            if (userDao.saveShipping(address, phone, userID) == 1) {
                sqlSession.commit();
                return new APIResponse<>(200, "配送信息已保存");
            } else {
                sqlSession.rollback();
                return new APIResponse<>(500, "配送信息保存失败");
            }
        } catch (Exception e) {
            return new APIResponse<>(401, "保存操作出现异常：" + e.getMessage());
        }
    }
}
