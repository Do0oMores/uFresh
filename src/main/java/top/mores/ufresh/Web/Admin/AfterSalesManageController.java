package top.mores.ufresh.Web.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.After_sales;
import top.mores.ufresh.Service.Admin.AfterSalesManageService;

import java.util.List;

@Controller
public class AfterSalesManageController {
    @Autowired
    private AfterSalesManageService afterSalesManageService;

    /**
     * 获取全部售后订单接口
     *
     * @return 全部售后订单
     */
    @PostMapping("/fetch-aftersales")
    @ResponseBody
    public ResponseEntity<?> fetchAfterSales() {
        APIResponse<List<After_sales>> response = afterSalesManageService.getAfterSales();
        return ResponseEntity.ok(response);
    }

    /**
     * 查找售后订单
     *
     * @param after_sales 查找条件
     * @return 查找结果
     */
    @PostMapping("/selectAfterSales")
    @ResponseBody
    public ResponseEntity<?> selectAfterSales(@RequestBody After_sales after_sales) {
        APIResponse<List<After_sales>> response = afterSalesManageService.selectAfterSales(after_sales);
        return ResponseEntity.ok(response);
    }

    /**
     * 管理员编辑售后订单状态
     *
     * @param after_sales 售后订单状态
     * @return 更新结果
     */
    @PostMapping("/admin-edit-aftersales")
    @ResponseBody
    public ResponseEntity<?> adminEditAfterSalesStatus(@RequestBody After_sales after_sales) {
        APIResponse<Void> response = afterSalesManageService.updateAfterSalesStatus(after_sales);
        return ResponseEntity.ok(response);
    }
}
