package top.mores.ufresh.Service.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.Service.User.UserInformationService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ImageUploadService {

    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private UserInformationService service;
    private static final Logger log = LoggerFactory.getLogger(ImageUploadService.class);
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png");

    @Transactional
    public APIResponse<String> uploadImage(MultipartFile file, Integer user_id) {
        try {
            if (file == null || file.isEmpty()) {
                return new APIResponse<>(500, "上传失败：文件为空");
            }

            String fileName = file.getOriginalFilename();
            if (fileName == null || fileName.isEmpty()) {
                return new APIResponse<>(500, "上传失败：文件名无效");
            }

            String fileType = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : "";
            if (!ALLOWED_EXTENSIONS.contains(fileType.toLowerCase())) {
                return new APIResponse<>(500, "上传失败：不支持的文件类型");
            }

            if (!fileName.matches("^[a-zA-Z0-9._-]+$")) {
                return new APIResponse<>(500, "上传失败：文件名包含非法字符");
            }

            String datePath = new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
            String localDir = uploadPath + "/uploads" + datePath + "/";
            File uploadDirFile = new File(localDir);
            if (!uploadDirFile.exists() && !uploadDirFile.mkdirs()) {
                return new APIResponse<>(500, "上传失败：无法创建目录");
            }

            String uuidFileName = UUID.randomUUID().toString().replace("-", "");
            String realFileName = uuidFileName + fileType;

            Path normalizedPath = Paths.get(localDir, realFileName).normalize();
            if (!normalizedPath.startsWith(uploadPath)) {
                return new APIResponse<>(500, "上传失败：非法文件路径");
            }

            File dest = normalizedPath.toFile();
            file.transferTo(dest);

            String avatarUrl = "uploads" + datePath + "/" + realFileName;

            if (service.saveAvatarUrl(user_id, avatarUrl)) {
                log.info("用户 [{}] 成功上传文件 [{}]，路径 [{}]", user_id, fileName, normalizedPath);
                return new APIResponse<>(200, "上传成功", avatarUrl);
            } else {
                Files.deleteIfExists(normalizedPath);
                return new APIResponse<>(500, "上传保存失败");
            }
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return new APIResponse<>(500, "上传失败：" + e.getMessage());
        }
    }
}
