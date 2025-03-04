package top.mores.ufresh.DAO;

import org.springframework.stereotype.Repository;
import top.mores.ufresh.POJO.After_sales;

import java.util.List;

@Repository
public interface AfterSalesDao {

    List<After_sales> getAfterSalesList();
}
