package top.mores.ufresh.Web.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;
import top.mores.ufresh.Service.Admin.CommodityManageService;
import top.mores.ufresh.Service.File.ImageUploadService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CommodityManageController {
    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private CommodityManageService commodityService;

    /**
     * 添加商品请求
     *
     * @param file          商品图片文件
     * @param commodityName 商品名
     * @param type          商品类型
     * @param price         商品价格
     * @param inventory     商品库存
     * @param description   商品介绍
     * @param support       商品供应商
     * @param mfg           商品生产日期
     * @param exp           商品过期时间
     * @return 添加结果
     */
    @PostMapping("addCommodityData")
    @ResponseBody
    public ResponseEntity<?> addCommodity(@RequestParam("file") MultipartFile file,
                                          @RequestParam("commodity_name") String commodityName,
                                          @RequestParam("type") String type,
                                          @RequestParam("price") Double price,
                                          @RequestParam("inventory") Integer inventory,
                                          @RequestParam("description") String description,
                                          @RequestParam("support") String support,
                                          @RequestParam("mfg") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate mfg,
                                          @RequestParam("exp") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate exp) {
        Commodity commodity = new Commodity();
        commodity.setCommodity_name(commodityName);
        commodity.setType(type);
        commodity.setPrice(price);
        commodity.setInventory(inventory);
        commodity.setDescription(description);
        commodity.setSupport(support);
        commodity.setExp(exp);
        commodity.setMfd(mfg);

        APIResponse<String> response = imageUploadService.addCommodityWithImage(file,
                commodity);
        return ResponseEntity.ok(response);
    }

    /**
     * 获取全部商品信息
     *
     * @return 全部商品信息
     */
    @PostMapping("/fetch-commodities")
    @ResponseBody
    public ResponseEntity<?> fetchCommodity() {
        APIResponse<List<Commodity>> response = commodityService.getAllCommodity();
        return ResponseEntity.ok(response);
    }

    /**
     * 查询商品
     *
     * @param commodity 传入的商品信息
     * @return 查询结果
     */
    @PostMapping("/select-commodities")
    @ResponseBody
    public ResponseEntity<?> selectCommodity(@RequestBody Commodity commodity) {
        APIResponse<List<Commodity>> response = commodityService.selectCommodities(commodity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload-commodity-image")
    @ResponseBody
    public ResponseEntity<?> uploadCommodityImage(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("commodity_id") Integer commodity_id) {
        APIResponse<String> response=imageUploadService.updateCommodityImage(file,commodity_id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/edit-commodity")
    @ResponseBody
    public ResponseEntity<?> editCommodity(@RequestBody Commodity commodity) {
        APIResponse<Void> response=commodityService.updateCommodityData(commodity);
        return ResponseEntity.ok(response);
    }
}
