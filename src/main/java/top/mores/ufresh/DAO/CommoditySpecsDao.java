package top.mores.ufresh.DAO;

import org.springframework.stereotype.Repository;
import top.mores.ufresh.POJO.Commodity_specs;

import java.util.List;

@Repository
public interface CommoditySpecsDao {

    List<Commodity_specs> getSpecByCommodityID(int commodityID);

    double getPrice(int commodity_id, String spec);
}
