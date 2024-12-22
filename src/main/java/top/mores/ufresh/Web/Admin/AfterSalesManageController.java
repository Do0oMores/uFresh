package top.mores.ufresh.Web.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.After_sales;
import top.mores.ufresh.Service.Admin.AfterSalesManageService;

import java.util.List;

@Controller
public class AfterSalesManageController {
    @Autowired
    private AfterSalesManageService afterSalesManageService;

    @PostMapping("/fetch-aftersales")
    @ResponseBody
    public ResponseEntity<?> fetchAfterSales(){
        APIResponse<List<After_sales>> response=afterSalesManageService.getAfterSales();
        return ResponseEntity.ok(response);
    }
}
