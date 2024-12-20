package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.CommodityDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;

import java.util.List;

@Service
public class CommodityManageService {

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

    /**
     * 根据商品名、商品类型、生产日期、过期时间、生产商查询商品
     *
     * @param commodity 传入的商品信息
     * @return 查询结果
     */
    public APIResponse<List<Commodity>> selectCommodities(Commodity commodity) {
        if (commodity == null) {
            return new APIResponse<>(400, "查询条件不能为空");
        }
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            List<Commodity> commodities = commodityDao.selectCommodity(commodity);

            if (!commodities.isEmpty()) {
                return new APIResponse<>(200, "共找到 " + commodities.size() + " 条数据", commodities);
            } else {
                return new APIResponse<>(404, "无结果");
            }
        } catch (PersistenceException e) {
            return new APIResponse<>(500, "数据库查询错误：" + e.getMessage());
        } catch (Exception e) {
            return new APIResponse<>(500, "发生未知错误：" + e.getMessage());
        }
    }
}
