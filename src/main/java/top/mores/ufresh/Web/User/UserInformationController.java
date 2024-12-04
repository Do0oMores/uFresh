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

    @PostMapping("/fetch-information")
    @ResponseBody
    public ResponseEntity<?> fetchInformation(@RequestBody User user) {
        Map<String, Object> response = userInformationService.getUserInformation(user.getUser_id());
        return ResponseEntity.ok(response);
    }
}
