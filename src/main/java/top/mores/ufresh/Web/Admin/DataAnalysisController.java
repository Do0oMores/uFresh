package top.mores.ufresh.Web.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;
import top.mores.ufresh.Service.Admin.DataAnalysisService;

import java.util.List;

@Controller
public class DataAnalysisController {
    @Autowired
    private DataAnalysisService dataAnalysisService;

    /**
     * 获取商品库存信息
     *
     * @return 商品库存
     */
    @PostMapping("/getCommoditiesInventory")
    @ResponseBody
    public ResponseEntity<?> getCommoditiesInventory() {
        APIResponse<List<Commodity>> response = dataAnalysisService.getCommoditiesInventory();
        return ResponseEntity.ok(response);
    }
}
