package top.mores.ufresh.DAO;

import top.mores.ufresh.POJO.Commodity;

import java.util.List;

public interface CommodityDao {
    Commodity getCommodityByName(String name);

    int addCommodity(Commodity commodity);

    List<Commodity> fetchCommodity();

    List<Commodity> fetchUserIndexCommodity(String status);
}
