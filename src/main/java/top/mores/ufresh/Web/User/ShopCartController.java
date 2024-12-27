package top.mores.ufresh.Web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Cart_items;
import top.mores.ufresh.Service.User.ShopCartService;

@Controller
public class ShopCartController {
    @Autowired
    private ShopCartService shopCartService;

    @PostMapping("/add-to-cart")
    @ResponseBody
    public ResponseEntity<?> addCommodityToCart(@RequestBody Cart_items cart_items) {
        System.out.println(cart_items);
        APIResponse<Void> response=shopCartService.addCommodityToCart(cart_items);
        return ResponseEntity.ok(response);
    }
}
