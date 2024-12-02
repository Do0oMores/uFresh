package top.mores.ufresh.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.mores.ufresh.POJO.User;
import top.mores.ufresh.Service.RegisterService;

import java.util.Map;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody User user) {
        Map<Integer, String> responseData = registerService.addUser(
                user.getUser_name(),
                user.getPassword(),
                user.getPhone());

        return ResponseEntity.ok(responseData);
    }
}
