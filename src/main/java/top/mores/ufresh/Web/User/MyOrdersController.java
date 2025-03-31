package top.mores.ufresh.Web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Order_items;
import top.mores.ufresh.POJO.Orders;
import top.mores.ufresh.Service.User.MyOrdersService;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class MyOrdersController {
    @Autowired
    private MyOrdersService myOrdersService;

    /**
     * 获取用户订单信息
     *
     * @param orders 用户ID
     * @return 订单信息
     */
    @PostMapping("/getOrders")
    @ResponseBody
    public ResponseEntity<?> getOrders(@RequestBody Orders orders) {
        APIResponse<Map<String, Object>> response = myOrdersService.getOrderItems(orders.getUser_id());
        return ResponseEntity.ok(response);
    }

    /**
     * 提交订单
     *
     * @param orders 订单信息
     * @return 提交结果
     */
    @PostMapping("/submit-order")
    @ResponseBody
    public ResponseEntity<?> submitOrder(@RequestBody Orders orders) {
        orders.setStatus("待处理");
        LocalDateTime now = LocalDateTime.now();
        orders.setCreated_time(now);
        APIResponse<Void> response = myOrdersService.updateOrderStatusByOrderUUID(orders);
        return ResponseEntity.ok(response);
    }

    /**
     * 取消订单接口
     *
     * @param orders 取消的订单号
     * @return 取消结果
     */
    @PostMapping("/cancel-order")
    @ResponseBody
    public ResponseEntity<?> cancelOrder(@RequestBody Orders orders) {
        orders.setStatus("已取消");
        APIResponse<Void> response = myOrdersService.updateOrderStatusByOrderUUID(orders);
        return ResponseEntity.ok(response);
    }

    /**
     * 减少订单内商品接口
     *
     * @param order_items 指定的订单内商品
     * @return 处理结果
     */
    @PostMapping("/decreaseQuantity1")
    @ResponseBody
    public ResponseEntity<?> decreaseQuantity1(@RequestBody Order_items order_items) {
        APIResponse<Void> response = myOrdersService.decreaseOrderQuantity(order_items);
        return ResponseEntity.ok(response);
    }

    /**
     * 移除订单内商品
     *
     * @param order_items 指定的订单内商品
     * @return 移除结果
     */
    @PostMapping("/removeOrderItem")
    @ResponseBody
    public ResponseEntity<?> removeOrderItem(@RequestBody Order_items order_items) {
        APIResponse<Void> response = myOrdersService.deleteOrderItem(order_items);
        return ResponseEntity.ok(response);
    }

    /**
     * 增加订单内商品数量
     *
     * @param order_items 指定的订单商品
     * @return 增加结果
     */
    @PostMapping("/increaseQuantity1")
    @ResponseBody
    public ResponseEntity<?> increaseQuantity1(@RequestBody Order_items order_items) {
        APIResponse<Void> response = myOrdersService.increaseQuantity(order_items);
        return ResponseEntity.ok(response);
    }

    /**
     * 移除(取消)订单
     *
     * @param orders 订单号
     * @return 移除结果
     */
    @PostMapping("/clearOrder")
    @ResponseBody
    public ResponseEntity<?> clearOrder(@RequestBody Orders orders) {
        APIResponse<Void> response = myOrdersService.clearOrder(orders);
        return ResponseEntity.ok(response);
    }
}
