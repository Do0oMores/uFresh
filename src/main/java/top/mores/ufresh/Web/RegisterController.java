package top.mores.ufresh.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Mail;
import top.mores.ufresh.POJO.User;
import top.mores.ufresh.Service.RegisterService;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 处理/register 请求
     *
     * @param user User接收
     * @return 响应体
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody User user) {
        APIResponse<Void> responseData = registerService.addUser(
                user.getUser_name(),
                user.getPassword(),
                user.getEmail());
        return ResponseEntity.ok(responseData);
    }

    /**
     * 发送验证码请求
     *
     * @param mail 传入邮箱字段
     * @return 发送结果
     */
    @PostMapping("/mail")
    @ResponseBody
    public ResponseEntity<?> mail(@RequestBody Mail mail) {
        APIResponse<Void> responseData = registerService.mail(mail.getEmail());
        return ResponseEntity.ok(responseData);
    }

    /**
     * 验证邮箱验证码
     *
     * @param mail 传入邮箱和验证码
     * @return 验证结果
     */
    @PostMapping("/verify")
    @ResponseBody
    public ResponseEntity<?> verify(@RequestBody Mail mail) {
        APIResponse<Void> responseData = registerService.verifyCode(mail.getCode());
        return ResponseEntity.ok(responseData);
    }
}
