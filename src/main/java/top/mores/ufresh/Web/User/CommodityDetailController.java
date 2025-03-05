package top.mores.ufresh.Web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Comment;
import top.mores.ufresh.POJO.Commodity;
import top.mores.ufresh.POJO.Commodity_specs;
import top.mores.ufresh.Service.User.CommodityDetailService;

import java.util.List;

@Controller
public class CommodityDetailController {
    @Autowired
    private CommodityDetailService commodityDetailService;

    /**
     * 拉取商品详情信息
     *
     * @param commodity 传入商品ID
     * @return 商品详情信息
     */
    @PostMapping("/fetch-commodity-detail")
    @ResponseBody
    public ResponseEntity<?> getCommodityDetail(@RequestBody Commodity commodity) {
        APIResponse<Commodity> response = commodityDetailService.getCommodityDetail(commodity.getCommodity_id());
        return ResponseEntity.ok(response);
    }

    /**
     * 拉取商品规格
     *
     * @param commodity 传入商品ID
     * @return 商品规格
     */
    @PostMapping("/fetch-commodity-spec")
    @ResponseBody
    public ResponseEntity<?> getCommoditySpec(@RequestBody Commodity commodity) {
        APIResponse<List<Commodity_specs>> response = commodityDetailService.getCommoditySpecs(commodity.getCommodity_id());
        return ResponseEntity.ok(response);
    }

    /**
     * 拉取评论信息接口
     *
     * @param comment 商品ID
     * @return 评论信息
     */
    @PostMapping("/fetch-comments")
    @ResponseBody
    public ResponseEntity<?> getComments(@RequestBody Comment comment) {
        APIResponse<List<Comment>> response = commodityDetailService.getComments(comment.getCommodity_id());
        return ResponseEntity.ok(response);
    }
}
