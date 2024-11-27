import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;
import top.mores.ufresh.POJO.User;

import java.util.List;

public class UserDaoTest {
    @Test
    public void test() {
        SqlSession session= MybatisUtils.getSqlSession();

        UserDao userDao= session.getMapper(UserDao.class);
        List<User> userList=userDao.getUserList();

        for(User user:userList){
            System.out.println(user);
        }
        session.close();
    }

}
