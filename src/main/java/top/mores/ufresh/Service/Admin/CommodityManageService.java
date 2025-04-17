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
     * 通过商品ID获取商品
     *
     * @param id 商品ID
     * @return 商品信息
     */
    public Commodity getCommodityById(int id) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            return commodityDao.getCommodityByID(id);
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

    /**
     * 更新商品图片
     *
     * @param commodity 商品信息
     * @return 是否更新成功
     */
    public boolean editCommodityImage(Commodity commodity) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            if (commodityDao.editCommodityImage(commodity.getImage(), commodity.getCommodity_id()) == 1) {
                session.commit();
                return true;
            } else {
                session.rollback();
                return false;
            }
        }
    }

    /**
     * 更新商品信息
     *
     * @param commodity 新的商品数据
     * @return 更新结果
     */
    public APIResponse<Void> updateCommodityData(Commodity commodity) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            if (commodityDao.editCommodity(commodity) == 1) {
                session.commit();
                return new APIResponse<>(200, "数据更新成功");
            } else {
                session.rollback();
                return new APIResponse<>(404, "数据更新发生错误");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }

    /**
     * 删除商品
     *
     * @param commodity 删除的商品
     * @return 删除结果
     */
    public APIResponse<Void> deleteCommodity(Commodity commodity) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            if (commodityDao.deleteCommodity(commodity.getCommodity_id()) == 1) {
                session.commit();
                return new APIResponse<>(200, "该商品已删除");
            } else {
                session.rollback();
                return new APIResponse<>(404, "删除商品时发生错误，可能是没有找到该商品");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }
}
