package top.mores.ufresh.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.mores.ufresh.POJO.User;
import top.mores.ufresh.Service.LoginService;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 处理前端/login请求
     *
     * @param user 附带的请求值使用User接收
     * @return 响应体
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody User user) {
        String username = user.getUser_name();
        String password = user.getPassword();
        Map<String, Object> response = loginService.loginVerify(username, password);
        return ResponseEntity.ok(response);
    }
}
