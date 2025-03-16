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

import java.util.List;
import java.util.Map;

@Controller
public class ShopCartController {
    @Autowired
    private ShopCartService shopCartService;

    /**
     * 将商品加入购物车
     *
     * @param cart_items 加入购物车的商品
     * @return 加入结果
     */
    @PostMapping("/add-to-cart")
    @ResponseBody
    public ResponseEntity<?> addCommodityToCart(@RequestBody Cart_items cart_items) {
        APIResponse<Void> response = shopCartService.addCommodityToCart(cart_items);
        return ResponseEntity.ok(response);
    }

    /**
     * 拉取购物车信息
     *
     * @param cart_items 用户ID
     * @return 购物车物品信息
     */
    @PostMapping("/fetch-cart")
    @ResponseBody
    public ResponseEntity<?> fetchCart(@RequestBody Cart_items cart_items) {
        APIResponse<List<Cart_items>> response = shopCartService.getUserCart(cart_items.getUser_id());
        return ResponseEntity.ok(response);
    }

    /**
     * 购物车结算接口
     *
     * @param requestBody 结算的购物车商品信息
     * @return 结算订单生成结果
     */
    @PostMapping("/checkout")
    @ResponseBody
    public ResponseEntity<?> checkout(@RequestBody Map<String, List<Cart_items>> requestBody) {
        List<Cart_items> cartItems = requestBody.get("items");
        APIResponse<Void> response = shopCartService.builtOrder(cartItems);
        return ResponseEntity.ok(response);
    }

    /**
     * 根据商品名查询购物车内商品
     *
     * @param cart_items 商品名与用户ID
     * @return 查询结果
     */
    @PostMapping("/select-items")
    @ResponseBody
    public ResponseEntity<?> selectItems(@RequestBody Cart_items cart_items) {
        APIResponse<List<Cart_items>> response = shopCartService.selectCartItems(cart_items);
        return ResponseEntity.ok(response);
    }
}
