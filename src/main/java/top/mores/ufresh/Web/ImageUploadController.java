package top.mores.ufresh.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.User;
import top.mores.ufresh.Service.File.ImageUploadService;
import top.mores.ufresh.Service.User.UserIndexService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUpload;
    @Autowired
    private UserIndexService userIndexService;

    /**
     * 保存头像图片文件
     *
     * @param file 图片文件
     * @return 保存结果
     */
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file,
                                         Integer user_id) {
        APIResponse<String> response = imageUpload.uploadImage(file, user_id);
        return ResponseEntity.ok(response);
    }

    /**
     * 请求用户头像url
     *
     * @param user 用户ID
     * @return 头像URL
     */
    @PostMapping("/indexAvatarImageSync")
    @ResponseBody
    public ResponseEntity<?> indexAvatarImageSync(@RequestBody User user) {
        String avatarUrl = userIndexService.syncAvatarImage(user.getUser_id());
        Map<String, String> response = new HashMap<>();
        response.put("avatarUrl", avatarUrl);
        return ResponseEntity.ok(response);
    }
}
