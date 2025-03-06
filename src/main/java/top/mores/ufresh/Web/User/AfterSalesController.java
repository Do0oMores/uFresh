package top.mores.ufresh.Web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Order_items;
import top.mores.ufresh.Service.User.AfterSalesService;

import java.util.List;

@Controller
public class AfterSalesController {
    @Autowired
    private AfterSalesService afterSalesService;

    /**
     * 申请售后订单接口
     *
     * @param order_items 订单号
     * @return 订单内商品信息
     */
    @PostMapping("/fetch-afterSalesItems")
    @ResponseBody
    public ResponseEntity<?> fetchAfterSalesItems(@RequestBody Order_items order_items) {
        APIResponse<List<Order_items>> response = afterSalesService.getAfterSalesItems(order_items);
        return ResponseEntity.ok(response);
    }
}
