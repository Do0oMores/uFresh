package top.mores.ufresh.Web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;
import top.mores.ufresh.Service.User.UserIndexService;

import java.util.List;

@Controller
public class UserIndexController {

    @Autowired
    private UserIndexService userIndexService;

    /**
     * 获取用户主页商品信息
     *
     * @return 主页商品
     */
    @PostMapping("/getUserIndexCommodity")
    @ResponseBody
    public ResponseEntity<?> returnUserIndexCommodity() {
        APIResponse<List<Commodity>> response = userIndexService.fetchCommodities();
        return ResponseEntity.ok(response);
    }

    /**
     * 查询商品信息接口
     *
     * @param commodity 商品类型与商品名
     * @return 查询结果
     */
    @PostMapping("/searchCommodity")
    @ResponseBody
    public ResponseEntity<?> searchCommodity(@RequestBody Commodity commodity) {
        APIResponse<List<Commodity>> response = userIndexService.searchCommodities(commodity);
        return ResponseEntity.ok(response);
    }
}
