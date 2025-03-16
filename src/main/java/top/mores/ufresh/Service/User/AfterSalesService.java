package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.AfterSalesDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Order_items;

import java.util.List;

@Service
public class AfterSalesService {

    /**
     * 获取申请售后商品信息
     *
     * @param order_items 订单号
     * @return 商品信息
     */
    public APIResponse<List<Order_items>> getAfterSalesItems(Order_items order_items) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            AfterSalesDao afterSalesDao = session.getMapper(AfterSalesDao.class);
            String orderUUID = order_items.getOrder_uuid();
            List<Order_items> orderItems = afterSalesDao.getAfterSalesCommodity(orderUUID);
            System.out.println(orderItems);
            if (!orderItems.isEmpty()) {
                return new APIResponse<>(200, orderItems);
            } else {
                return new APIResponse<>(404, "未找到数据信息");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }
}
