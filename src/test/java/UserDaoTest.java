import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;
import top.mores.ufresh.POJO.User;

public class UserDaoTest {
    @Test
    public void test() {
        SqlSession session = MybatisUtils.getSqlSession();
        UserDao userDao = session.getMapper(UserDao.class);
        String test = "测试";
        User data = userDao.getUserPassword(test);
        String password = data.getPassword();
        System.out.println(password);
        session.close();
    }
}
