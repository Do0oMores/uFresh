package top.mores.ufresh.DAO;

import org.springframework.stereotype.Repository;
import top.mores.ufresh.POJO.User;

import java.util.List;

@Repository
public interface UserDao {
    String getUserPassword(String userName);

    String checkUser(String userName);

    int addUser(User user);

    User returnUserData(Integer id);

    Integer getUserID(String userName);

    int saveUserData(String user_name, String password, String email, Integer user_id);

    int saveUserAvatar(String avatar_url, Integer user_id);

    int saveShipping(String address, String phone, Integer user_id);

    int checkRole(String user_name);

    List<User> fetchUsers();

    List<User> selectUser(String username);

    int saveEditUserData(String user_name, String email, String phone, boolean admin_enabled, Integer user_id);

    String syncAvatar(Integer user_id);
}
