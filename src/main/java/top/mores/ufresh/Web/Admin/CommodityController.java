package top.mores.ufresh.Web.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;
import top.mores.ufresh.Service.File.ImageUploadService;

import java.time.LocalDate;

@Controller
public class CommodityController {
    @Autowired
    private ImageUploadService imageUploadService;

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
}
