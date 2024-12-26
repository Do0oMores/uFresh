package top.mores.ufresh.DAO;

import top.mores.ufresh.POJO.Commodity_specs;

import java.util.List;

public interface CommoditySpecsDao {

    List<Commodity_specs> getSpecByCommodityID(int commodityID);
}
