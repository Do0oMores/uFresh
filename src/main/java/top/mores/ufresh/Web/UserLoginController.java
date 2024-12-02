package top.mores.ufresh.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.mores.ufresh.POJO.User;
import top.mores.ufresh.Service.UserLoginService;

import java.util.Map;

@RestController
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody User user) {
        String username = user.getUser_name();
        String password = user.getPassword();
        Map<Integer, String> response = userLoginService.loginVerify(username, password);
        return ResponseEntity.ok(response);
    }
}
