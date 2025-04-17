package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.WasteDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;

import java.util.List;

@Service
public class WasteService {

    /**
     * 获取商品信息
     *
     * @return 返回商品信息
     */
    public APIResponse<List<Commodity>> fetchWasteCommodities() {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            WasteDao wasteDao = session.getMapper(WasteDao.class);
            List<Commodity> data = wasteDao.getWasteCommodities();
            if (!data.isEmpty()) {
                return new APIResponse<>(200, data);
            } else {
                return new APIResponse<>(404, "没有商品信息");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, e.getMessage());
        }
    }
}
