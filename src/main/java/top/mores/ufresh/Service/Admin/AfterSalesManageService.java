package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.AfterSalesDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.After_sales;

import java.util.List;

@Service
public class AfterSalesManageService {

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
}
