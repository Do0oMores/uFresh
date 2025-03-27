package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.CommodityDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;

import java.util.List;

@Service
public class CommodityOriginService {

    /**
     * 查询商品详细信息
     *
     * @param commodity 商品名
     * @return 商品信息
     */
    public APIResponse<List<Commodity>> selectCommodityOrigin(Commodity commodity) {
        try (SqlSession sqlSession = MybatisUtils.getSqlSession()) {
            CommodityDao commodityDao = sqlSession.getMapper(CommodityDao.class);
            List<Commodity> commodities = commodityDao.selectCommodityOrigin(commodity);
            if (commodities != null) {
                return new APIResponse<>(200, commodities);
            } else {
                return new APIResponse<>(404, "没有查询到相关商品信息");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }
}
