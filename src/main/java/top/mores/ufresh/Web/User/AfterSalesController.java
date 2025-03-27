package top.mores.ufresh.Web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.After_sales;
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

    /**
     * 提交售后订单申请
     *
     * @param file       佐证图片文件
     * @param order_uuid 订单号
     * @param reason     售后原因
     * @param type       售后类型
     * @return
     */
    @PostMapping("/uploadAfterSales")
    @ResponseBody
    public ResponseEntity<?> uploadAfterSalesImage(@RequestParam("file") MultipartFile file,
                                                   String order_uuid,
                                                   String reason,
                                                   String type) {
        After_sales after_sales = new After_sales();
        after_sales.setOrder_uuid(order_uuid);
        after_sales.setReasons(reason);
        after_sales.setService_type(type);
        APIResponse<Void> response = afterSalesService.uploadAfterSalesImage(file, after_sales);
        return ResponseEntity.ok(response);
    }
}
