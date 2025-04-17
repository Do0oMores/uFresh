package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.OrderItemsDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Order_items;

import java.util.List;

@Service
public class CommentService {

    public APIResponse<List<Order_items>> getCommentCommodities(String order_uuid) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            OrderItemsDao orderItemsDao = session.getMapper(OrderItemsDao.class);
            List<Order_items> data = orderItemsDao.getOrderItemsByUUID(order_uuid);
            if (!data.isEmpty()) {
                return new APIResponse<>(200, data);
            } else {
                return new APIResponse<>(404, "没有查找到有关该订单的商品信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }
}
