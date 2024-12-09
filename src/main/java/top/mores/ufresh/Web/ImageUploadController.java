package top.mores.ufresh.Web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.Service.File.ImageUploadService;

import java.util.Enumeration;

@Controller
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUpload;

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
}
