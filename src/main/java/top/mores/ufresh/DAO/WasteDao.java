package top.mores.ufresh.DAO;

import top.mores.ufresh.POJO.Commodity;
import top.mores.ufresh.POJO.Waste;

import java.util.List;

public interface WasteDao {
    List<Commodity> getWasteCommodities();

    List<Waste> getAllWasteLog();
}
