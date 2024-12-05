package top.mores.ufresh.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.mores.ufresh.Service.User.UserInformationService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ImageUploadController {

    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private UserInformationService service;

    /**
     * 保存头像图片文件
     *
     * @param file 图片文件
     * @return 保存结果
     */
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, Integer user_id) {
        Map<String, Object> response = new HashMap<>();
        try {
            String fileName = file.getOriginalFilename();
            File uploadDirFile = new File(uploadPath);
            if (!uploadDirFile.exists()) {
                if (!uploadDirFile.mkdir()) {
                    response.put("code", 500);
                    response.put("msg", "上传失败：保存文件夹创建失败");
                    response.put("filename", fileName);
                }
            }

            String filepath = uploadPath + "/" + fileName;
            File dest = new File(filepath);
            file.transferTo(dest);
            String avatarUrl = "/uploads/" + fileName;
            if (service.saveAvatarUrl(user_id, avatarUrl)) {
                response.put("code", 200);
                response.put("msg", "上传成功");
                response.put("filename", fileName);
            } else {
                response.put("code", 500);
                response.put("msg", "上传保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("code", 500);
            response.put("msg", "上传失败");
        }
        return ResponseEntity.ok(response);
    }
}
