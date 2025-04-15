package top.mores.ufresh.DAO;

import org.springframework.stereotype.Repository;
import top.mores.ufresh.POJO.Commodity;

import java.util.List;

@Repository
public interface CommodityDao {
    Commodity getCommodityByName(String name);

    int addCommodity(Commodity commodity);

    List<Commodity> fetchCommodity();

    List<Commodity> fetchUserIndexCommodity(String status);

    List<Commodity> selectCommodity(Commodity commodity);

    Commodity getCommodityByID(int id);

    int editCommodity(Commodity commodity);

    int editCommodityImage(String image,Integer commodity_id);

    List<Commodity> searchCommodity(Commodity commodity);

    List<Commodity> selectCommodityOrigin(Commodity commodity);

    int getCommodityQuantity(int Commodity_id);

    int reduceCommodityQuantity(int commodityId, int orderQuantity);

    List<Commodity> getAllCommoditiesInventory();
}
