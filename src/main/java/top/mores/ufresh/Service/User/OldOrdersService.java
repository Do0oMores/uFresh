package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.OldOrderItemsDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Old_order_items;

import java.util.List;

@Service
public class OldOrdersService {

    /**
     * 拉取历史订单信息
     *
     * @param userId 用户ID
     * @return 历史订单信息
     */
    public APIResponse<List<Old_order_items>> getOldOrderItemsByUserId(int userId) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            OldOrderItemsDao oldOrderItemsDao = session.getMapper(OldOrderItemsDao.class);
            List<Old_order_items> oldOrderItems = oldOrderItemsDao.getOldOrderItems(userId);
            if (oldOrderItems != null) {
                return new APIResponse<>(200, oldOrderItems);
            } else {
                return new APIResponse<>(404, "没有找到历史订单信息");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生预料之外的错误" + e.getMessage());
        }
    }
}
