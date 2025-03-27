package top.mores.ufresh.Web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;
import top.mores.ufresh.Service.User.CommodityOriginService;

import java.util.List;

@Controller
public class CommodityOriginController {
    @Autowired
    private CommodityOriginService commodityOriginService;

    /**
     * 查询商品详细信息接口
     *
     * @param commodity 商品名
     * @return 商品详细信息
     */
    @PostMapping("/getCommodityOrigin")
    @ResponseBody
    public ResponseEntity<?> getCommodityOrigin(@RequestBody Commodity commodity) {
        APIResponse<List<Commodity>> response = commodityOriginService.selectCommodityOrigin(commodity);
        return ResponseEntity.ok(response);
    }
}
