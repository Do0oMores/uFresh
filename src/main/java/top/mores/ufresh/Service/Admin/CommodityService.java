package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.CommodityDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;

import java.util.List;

@Service
public class CommodityService {

    /**
     * 通过商品名获取商品
     *
     * @param name 商品名
     * @return 商品
     */
    public Commodity getCommodityByName(String name) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            return commodityDao.getCommodityByName(name);
        }
    }

    /**
     * 添加商品
     *
     * @param commodity 商品
     * @return 添加结果（是否添加成功）
     */
    public boolean addCommodity(Commodity commodity) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            if (commodityDao.addCommodity(commodity) == 1) {
                session.commit();
                return true;
            } else {
                session.rollback();
                return false;
            }
        }
    }

    /**
     * 获取所有商品
     *
     * @return 所有商品
     */
    public APIResponse<List<Commodity>> getAllCommodity() {
        SqlSession session = MybatisUtils.getSqlSession();
        CommodityDao commodityDao = session.getMapper(CommodityDao.class);
        try {
            List<Commodity> commodities = commodityDao.fetchCommodity();
            return new APIResponse<>(200, commodities);
        } catch (Exception e) {
            return new APIResponse<>(500, "发生错误：" + e.getMessage());
        }
    }
}
