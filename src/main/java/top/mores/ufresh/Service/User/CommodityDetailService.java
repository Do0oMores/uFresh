package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.CommodityDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;

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
}
