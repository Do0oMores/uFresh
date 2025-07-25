package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.AfterSalesDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.NotificationDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.After_sales;
import top.mores.ufresh.POJO.Notification;
import top.mores.ufresh.Utils.NotificationMessage;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AfterSalesManageService {
    private final NotificationMessage notificationMessage;

    public AfterSalesManageService(NotificationMessage notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    /**
     * 获取全部售后订单信息
     *
     * @return 全部售后订单信息
     */
    public APIResponse<List<After_sales>> getAfterSales() {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            AfterSalesDao afterSalesDao = session.getMapper(AfterSalesDao.class);
            List<After_sales> afterSales = afterSalesDao.getAfterSalesList();
            return new APIResponse<>(200, afterSales);
        } catch (Exception e) {
            return new APIResponse<>(500, "发生错误：" + e.getMessage());
        }
    }

    /**
     * 查询售后订单
     *
     * @param afterSales 售后订单信息
     * @return 查询的售后订单信息
     */
    public APIResponse<List<After_sales>> selectAfterSales(After_sales afterSales) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            AfterSalesDao afterSalesDao = session.getMapper(AfterSalesDao.class);
            List<After_sales> after_sales = afterSalesDao.selectAfterSalesByAdmin(afterSales);
            if (after_sales != null) {
                return new APIResponse<>(200, after_sales);
            } else {
                return new APIResponse<>(404, "没有查询到相关售后订单");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误" + e.getMessage());
        }
    }

    /**
     * 管理员更改售后订单状态
     *
     * @param afterSales 售后订单状态
     * @return 更新结果
     */
    public APIResponse<Void> updateAfterSalesStatus(After_sales afterSales) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            AfterSalesDao afterSalesDao = session.getMapper(AfterSalesDao.class);
            NotificationDao notificationDao = session.getMapper(NotificationDao.class);

            Notification notification = new Notification();
            int result = afterSalesDao.updateAfterSalesStatus(afterSales);
            notification.setNotification_title(notificationMessage.getAfterSalesFinish().getTitle());
            notification.setNotification_content(notificationMessage.getAfterSalesFinish().getContent().replace("{order_uuid}", afterSales.getOrder_uuid()));
            notification.setType(notificationMessage.getAfterSalesFinish().getType());
            notification.setTime(LocalDateTime.now());
            notification.setOrder_uuid(afterSales.getOrder_uuid());

            int notiResult = notificationDao.addAfterSaleNotification(notification);
            if (result > 0 && notiResult == 1) {
                session.commit();
                return new APIResponse<>(200, "售后订单状态已更新");
            } else {
                session.rollback();
                return new APIResponse<>(500, "更新订单状态时出现错误");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }
}
