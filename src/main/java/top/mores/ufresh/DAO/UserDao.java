package top.mores.ufresh.DAO;

import org.springframework.stereotype.Repository;
import top.mores.ufresh.POJO.User;

@Repository
public interface UserDao {
    User getUserPassword(String userName);

    String checkUser(String userName);

    int addUser(User user);
}
