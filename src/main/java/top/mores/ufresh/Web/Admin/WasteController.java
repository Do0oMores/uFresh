package top.mores.ufresh.Web.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;
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
}
