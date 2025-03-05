package top.mores.ufresh.Web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Old_order_items;
import top.mores.ufresh.POJO.Orders;
import top.mores.ufresh.Service.User.OldOrdersService;

import java.util.List;

@Controller
public class OldOrderController {
    @Autowired
    private OldOrdersService oldOrdersService;

    /**
     * 拉取用户历史订单
     *
     * @param order 用户ID
     * @return 历史订单信息
     */
    @PostMapping("/fetch-old-orders")
    @ResponseBody
    public ResponseEntity<?> getOldOrders(@RequestBody Orders order) {
        APIResponse<List<Old_order_items>> response = oldOrdersService.getOldOrderItemsByUserId(order.getUser_id());
        return ResponseEntity.ok(response);
    }
}
