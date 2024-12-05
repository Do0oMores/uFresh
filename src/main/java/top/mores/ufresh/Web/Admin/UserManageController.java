package top.mores.ufresh.Web.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.Service.Admin.UserManageService;

import java.util.Map;

@Controller
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    @PostMapping("/fetch-users")
    @ResponseBody
    public ResponseEntity<?> fetchUsers() {
        Map<String, Object> response = userManageService.getUsers();
        return ResponseEntity.ok(response);
    }
}
