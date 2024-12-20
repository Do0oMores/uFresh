package top.mores.ufresh.Web.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.User;
import top.mores.ufresh.Service.Admin.UserManageService;

import java.util.List;

@Controller
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    /**
     * 返回全部用户信息
     *
     * @return 全部用户信息
     */
    @PostMapping("/fetch-users")
    @ResponseBody
    public ResponseEntity<?> fetchUsers() {
        APIResponse<List<User>> response = userManageService.getUsers();
        return ResponseEntity.ok(response);
    }

    /**
     * 查询用户
     *
     * @param user 用户名
     * @return 查询到的用户数据
     */
    @PostMapping("/select-user")
    @ResponseBody
    public ResponseEntity<?> selectUser(@RequestBody User user) {
        APIResponse<List<User>> response = userManageService.selectUserData(user);
        return ResponseEntity.ok(response);
    }

    /**
     * 保存管理员编辑后的用户数据
     *
     * @param user 用户
     * @return 保存结果
     */
    @PostMapping("/admin-edit-user")
    @ResponseBody
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        APIResponse<Void> response = userManageService.saveEditUser(user.getUser_name(),
                user.getAdmin_enabled(),
                user.getPhone(),
                user.getEmail(),
                user.getUser_id());
        return ResponseEntity.ok(response);
    }
}
