package top.mores.ufresh.Web.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Orders;
import top.mores.ufresh.Service.Admin.OrderManageService;

import java.util.List;

@Controller
public class OrderManageController {
    @Autowired
    private OrderManageService orderManageService;

    /**
     * 拉取所有订单信息
     *
     * @return 所有订单信息
     */
    @PostMapping("/fetch-orders")
    @ResponseBody
    public ResponseEntity<?> getOrders() {
        APIResponse<List<Orders>> response = orderManageService.getOrders();
        return ResponseEntity.ok(response);
    }
}
