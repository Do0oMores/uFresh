package top.mores.ufresh.Web.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;
import top.mores.ufresh.POJO.Waste;
import top.mores.ufresh.Service.Admin.WasteService;

import java.util.List;

@Controller
public class WasteController {
    @Autowired
    private WasteService wasteService;

    /**
     * 拉取商品信息
     *
     * @return 返回商品信息
     */
    @PostMapping("/fetchWasteCommodities")
    @ResponseBody
    public ResponseEntity<?> fetchWasteCommodities() {
        APIResponse<List<Commodity>> response = wasteService.fetchWasteCommodities();
        return ResponseEntity.ok(response);
    }

    /**
     * 拉取损耗记录
     *
     * @return 损耗记录
     */
    @PostMapping("/fetchLossRecords")
    @ResponseBody
    public ResponseEntity<?> fetchLossRecords() {
        APIResponse<List<Waste>> response = wasteService.fetchWastes();
        return ResponseEntity.ok(response);
    }

    /**
     * 添加损耗记录
     *
     * @param waste 损耗信息
     * @return 添加结果
     */
    @PostMapping("/addLossRecord")
    @ResponseBody
    public ResponseEntity<?> addLossRecord(@RequestBody Waste waste) {
        APIResponse<Void> response = wasteService.addWaste(waste);
        return ResponseEntity.ok(response);
    }
}
