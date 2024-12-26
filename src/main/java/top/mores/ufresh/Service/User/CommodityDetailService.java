package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.CommodityDao;
import top.mores.ufresh.DAO.CommoditySpecsDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;
import top.mores.ufresh.POJO.Commodity_specs;

import java.util.List;

@Service
public class CommodityDetailService {

    /**
     * 获取商品详情信息
     *
     * @param id 商品ID
     * @return 商品信息
     */
    public APIResponse<Commodity> getCommodityDetail(int id) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            Commodity commodityDetail = commodityDao.getCommodityByID(id);
            return new APIResponse<>(200, commodityDetail);
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }

    /**
     * 获取商品规格
     *
     * @param id 商品ID
     * @return 商品规格
     */
    public APIResponse<List<Commodity_specs>> getCommoditySpecs(int id) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CommoditySpecsDao commoditySpecsDao = session.getMapper(CommoditySpecsDao.class);
            List<Commodity_specs> specsList = commoditySpecsDao.getSpecByCommodityID(id);
            return new APIResponse<>(200, specsList);
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }
}
