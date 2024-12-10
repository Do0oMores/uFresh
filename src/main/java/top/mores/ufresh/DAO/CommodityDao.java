package top.mores.ufresh.DAO;

import top.mores.ufresh.POJO.Commodity;

public interface CommodityDao {
    Commodity getCommodityByName(String name);

    int addCommodity(Commodity commodity);
}
