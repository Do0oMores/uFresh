package top.mores.ufresh.Web.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 根据条件查询订单信息
     *
     * @param orders 传入的查询条件：订单号、用户名、订单状态
     * @return 订单查询结果
     */
    @PostMapping("/select_orders")
    @ResponseBody
    public ResponseEntity<?> selectOrders(@RequestBody Orders orders) {
        APIResponse<List<Orders>> response = orderManageService.getOrdersByConditions(orders);
        return ResponseEntity.ok(response);
    }

    /**
     * 管理员编辑订单状态接口
     *
     * @param orders 订单信息
     * @return 编辑结果
     */
    @PostMapping("/admin-edit-order-status")
    @ResponseBody
    public ResponseEntity<?> adminEditOrderStatus(@RequestBody Orders orders) {
        APIResponse<Void> response = orderManageService.editOrder(orders);
        return ResponseEntity.ok(response);
    }
}
