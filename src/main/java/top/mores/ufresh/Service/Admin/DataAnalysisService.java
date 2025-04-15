package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.CommodityDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;

import java.util.List;

@Service
public class DataAnalysisService {

    /**
     * 获取商品库存
     *
     * @return 商品库存信息
     */
    public APIResponse<List<Commodity>> getCommoditiesInventory() {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            List<Commodity> inventory = commodityDao.getAllCommoditiesInventory();
            if (inventory != null) {
                return new APIResponse<>(200, inventory);
            } else {
                return new APIResponse<>(404, "没有查询到相关库存数据");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }
}
