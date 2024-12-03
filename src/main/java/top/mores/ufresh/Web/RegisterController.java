package top.mores.ufresh.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.mores.ufresh.Component.MailSenderComponent;
import top.mores.ufresh.POJO.Mail;
import top.mores.ufresh.POJO.User;
import top.mores.ufresh.Service.RegisterService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private MailSenderComponent mailSender;

    /**
     * 处理/register 请求
     * @param user User接收
     * @return 响应体
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody User user) {
        Map<Integer, String> responseData = registerService.addUser(
                user.getUser_name(),
                user.getPassword(),
                user.getPhone());

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/test")
    @ResponseBody
    public ResponseEntity<?> test(@RequestBody Mail mail) {
        Map<Integer,String> responseData=new HashMap<>();
        boolean result= mailSender.sendMail(mail.getMail(),mail.getCode());
        if (result) {
            responseData.put(200,"发送成功");
        }else {
            responseData.put(500,"发送失败");
        }
        return ResponseEntity.ok(responseData);
    }
}
