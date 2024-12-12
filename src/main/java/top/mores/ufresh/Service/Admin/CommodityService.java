package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.CommodityDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.POJO.Commodity;

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
}
