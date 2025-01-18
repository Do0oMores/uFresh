package top.mores.ufresh.Service.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;
import top.mores.ufresh.Service.Admin.CommodityManageService;
import top.mores.ufresh.Service.User.UserInformationService;

import java.io.File;
import java.io.IOException;
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
    @Autowired
    private CommodityManageService commodityService;
    private static final Logger log = LoggerFactory.getLogger(ImageUploadService.class);
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png");

    /**
     * 上传图片
     *
     * @param file    图片文件
     * @param user_id 上传的用户id
     * @return 图片URL
     */
    @Transactional
    public APIResponse<String> uploadImage(MultipartFile file, Integer user_id) {
        try {
            String imageUrl = saveFile(file, uploadPath);

            if (service.saveAvatarUrl(user_id, imageUrl)) {
                log.info("用户 [{}] 成功上传文件，路径 [{}]", user_id, imageUrl);
                return new APIResponse<>(200, "上传成功", imageUrl);
            } else {
                return new APIResponse<>(500, "上传保存失败");
            }
        } catch (IllegalArgumentException e) {
            log.warn("文件上传失败：{}", e.getMessage());
            return new APIResponse<>(400, "上传失败：" + e.getMessage());
        } catch (IOException | SecurityException e) {
            log.error("文件上传失败", e);
            return new APIResponse<>(500, "上传失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("未知错误", e);
            return new APIResponse<>(500, "上传失败：未知错误");
        }
    }

    /**
     * 添加商品（包含商品图片）
     *
     * @param file      商品图片文件
     * @param commodity 商品
     * @return 商品图片URL
     */
    @Transactional
    public APIResponse<String> addCommodityWithImage(MultipartFile file,
                                                     Commodity commodity) {
        try {
            String commodityName = commodity.getCommodity_name();
            if (commodityService.getCommodityByName(commodityName) != null) {
                return new APIResponse<>(400, "新增失败：商品名已存在");
            }

            String imageUrl = saveFile(file, uploadPath);
            commodity.setImage(imageUrl);

            if (commodityService.addCommodity(commodity)) {
                log.info("商品 [{}] 成功新增，图片路径 [{}]", commodityName, imageUrl);
                return new APIResponse<>(200, "新增成功", imageUrl);
            } else {
                return new APIResponse<>(500, "新增失败：数据库保存失败");
            }
        } catch (IllegalArgumentException e) {
            log.warn("新增商品失败：{}", e.getMessage());
            return new APIResponse<>(400, "新增失败：" + e.getMessage());
        } catch (IOException | SecurityException e) {
            log.error("新增商品失败", e);
            return new APIResponse<>(500, "新增失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("未知错误", e);
            return new APIResponse<>(500, "新增失败：未知错误");
        }
    }

    /**
     * 文件校验保存
     *
     * @param file     文件
     * @param basePath 基础路径
     * @return 保存的路径
     * @throws IOException 抛出异常
     */
    private String saveFile(MultipartFile file, String basePath) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("文件为空");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("文件名无效");
        }

        String fileType = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : "";
        if (!ALLOWED_EXTENSIONS.contains(fileType.toLowerCase())) {
            throw new IllegalArgumentException("不支持的文件类型");
        }

        if (!fileName.matches("^[a-zA-Z0-9._-]+$")) {
            throw new IllegalArgumentException("文件名包含非法字符");
        }

        String datePath = new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
        String localDir = basePath + "/uploads" + datePath + "/";
        File uploadDirFile = new File(localDir);
        if (!uploadDirFile.exists() && !uploadDirFile.mkdirs()) {
            throw new IOException("无法创建目录");
        }

        String uuidFileName = UUID.randomUUID().toString().replace("-", "");
        String realFileName = uuidFileName + fileType;

        Path normalizedPath = Paths.get(localDir, realFileName).normalize();
        if (!normalizedPath.startsWith(uploadPath)) {
            throw new SecurityException("非法文件路径");
        }

        File dest = normalizedPath.toFile();
        file.transferTo(dest);

        return "/uploads" + datePath + "/" + realFileName;
    }

    /**
     * 更新商品图片
     *
     * @param file        图片文件
     * @param commodityID 商品数据
     * @return 更新结果
     */
    @Transactional
    public APIResponse<String> updateCommodityImage(MultipartFile file, Integer commodityID) {
        try {
            Commodity commodity = commodityService.getCommodityById(commodityID);
            if (commodity == null) {
                return new APIResponse<>(400, "更新失败：商品不存在");
            }
            String imageUrl = saveFile(file, uploadPath);

            commodity.setImage(imageUrl);
            if (commodityService.updateCommodity(commodity)) {
                log.info("商品 [{}] 图片更新成功，新图片路径 [{}]", commodityID, imageUrl);
                return new APIResponse<>(200, "图片更新成功", imageUrl);
            } else {
                return new APIResponse<>(500, "图片更新失败：数据库操作失败");
            }
        } catch (IllegalArgumentException e) {
            log.warn("更新商品图片失败：{}", e.getMessage());
            return new APIResponse<>(400, "更新失败：" + e.getMessage());
        } catch (IOException | SecurityException e) {
            log.error("更新商品图片失败", e);
            return new APIResponse<>(500, "更新失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("未知错误", e);
            return new APIResponse<>(500, "更新失败：未知错误");
        }
    }
}
