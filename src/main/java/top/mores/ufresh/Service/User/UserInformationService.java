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
}
