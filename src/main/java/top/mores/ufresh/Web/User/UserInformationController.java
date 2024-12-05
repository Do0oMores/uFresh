package top.mores.ufresh.Web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.User;
import top.mores.ufresh.Service.User.UserInformationService;

import java.util.Map;

@Controller
public class UserInformationController {
    @Autowired
    private UserInformationService userInformationService;

    /**
     * 同步获取用户信息
     *
     * @param user 用户ID
     * @return 用户信息
     */
    @PostMapping("/fetch-information")
    @ResponseBody
    public ResponseEntity<?> fetchInformation(@RequestBody User user) {
        Map<String, Object> response = userInformationService.getUserInformation(user.getUser_id());
        return ResponseEntity.ok(response);
    }

    /**
     * 保存用户信息
     *
     * @param user 用户
     * @return 保存结果
     */
    @PostMapping("/save-user")
    @ResponseBody
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        Map<Integer, String> response = userInformationService.saveUserInformation(
                user.getUser_name(),
                user.getPassword(),
                user.getEmail(),
                user.getUser_id()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save-shipping")
    @ResponseBody
    public ResponseEntity<?> saveShipping(@RequestBody User user) {
        Map<Integer, String> response = userInformationService.saveShippingInformation(
                user.getAddress(),
                user.getPhone(),
                user.getUser_id()
        );
        return ResponseEntity.ok(response);
    }
}
