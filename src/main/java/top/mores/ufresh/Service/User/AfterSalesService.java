package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.mores.ufresh.DAO.AfterSalesDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.OrdersDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.After_sales;
import top.mores.ufresh.POJO.Order_items;
import top.mores.ufresh.POJO.Orders;
import top.mores.ufresh.Service.File.ImageUploadService;

import java.util.List;

@Service
public class AfterSalesService {
    private final ImageUploadService imageUploadService = new ImageUploadService();
    @Value("${upload.path}")
    private String uploadPath;

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
            if (!orderItems.isEmpty()) {
                return new APIResponse<>(200, orderItems);
            } else {
                return new APIResponse<>(404, "未找到数据信息");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }

    /**
     * 售后申请提交
     *
     * @param file        售后申请图片
     * @param after_sales 售后申请信息
     * @return 提交结果
     */
    public APIResponse<Void> uploadAfterSalesImage(MultipartFile file, After_sales after_sales) {
        String imageURL;
        try {
            imageURL = imageUploadService.saveFile(file, uploadPath);
        } catch (Exception e) {
            return new APIResponse<>(500, "图片上传发生错误");
        }
        after_sales.setImage(imageURL);
        after_sales.setProgress("已提交");

        try (SqlSession session = MybatisUtils.getSqlSession()) {
            AfterSalesDao afterSalesDao = session.getMapper(AfterSalesDao.class);
            OrdersDao ordersDao = session.getMapper(OrdersDao.class);
            int result = afterSalesDao.insertAfterSalesImage(after_sales);
            if (result > 0) {
                session.commit();
                Orders orders = new Orders();
                orders.setStatus("申请售后");
                orders.setOrder_uuid(after_sales.getOrder_uuid());
                int status = ordersDao.updateOrderStatus(orders);
                if (status > 0) {
                    session.commit();
                } else {
                    session.rollback();
                    return new APIResponse<>(500, "修改订单状态失败");
                }
                return new APIResponse<>(200, "已提交售后申请");
            } else {
                session.rollback();
                return new APIResponse<>(404, "提交售后申请失败");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "提交售后申请时发生意料之外的错误：" + e.getMessage());
        }
    }
}
