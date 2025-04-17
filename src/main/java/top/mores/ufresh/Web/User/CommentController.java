package top.mores.ufresh.Web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Order_items;
import top.mores.ufresh.Service.User.CommentService;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/fetch-commentItems")
    @ResponseBody
    public ResponseEntity<?> fetchCommentItems(@RequestBody Order_items order_items) {
        APIResponse<List<Order_items>> response=commentService.getCommentCommodities(order_items.getOrder_uuid());
        return ResponseEntity.ok(response);
    }
}
