package top.mores.ufresh.DAO;

import top.mores.ufresh.POJO.Commodity;

import java.util.List;

public interface WasteDao {
    List<Commodity> getWasteCommodities();
}
