package top.mores.ufresh.DAO;

import org.springframework.stereotype.Repository;
import top.mores.ufresh.POJO.User;

@Repository
public interface UserDao {
    String getUserPassword(String userName);

    String checkUser(String userName);

    int addUser(User user);

    User returnUserData(Integer id);

    Integer getUserID(String userName);
}
