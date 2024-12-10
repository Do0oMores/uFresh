package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;

@Service
public class UserIndexService {

    /**
     * 同步用户导航栏头像图片
     *
     * @param userId 传入用户ID
     * @return 图片url
     */
    public String syncAvatarImage(Integer userId) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            UserDao userDao = session.getMapper(UserDao.class);
            return userDao.syncAvatar(userId);
        } catch (Exception e) {
            return null;
        }
    }
}
