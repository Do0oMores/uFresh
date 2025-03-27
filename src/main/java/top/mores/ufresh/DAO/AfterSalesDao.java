package top.mores.ufresh.DAO;

import org.springframework.stereotype.Repository;
import top.mores.ufresh.POJO.After_sales;
import top.mores.ufresh.POJO.Order_items;

import java.util.List;

@Repository
public interface AfterSalesDao {

    List<After_sales> getAfterSalesList();

    List<Order_items> getAfterSalesCommodity(String order_uuid);

    int insertAfterSalesImage(After_sales after_sales);

    List<After_sales> selectAfterSalesByAdmin(After_sales after_sales);
}
