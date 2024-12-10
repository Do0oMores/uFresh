package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.CommodityDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.POJO.Commodity;

@Service
public class CommodityService {

    public Commodity getCommodityByName(String name) {
        try(SqlSession session= MybatisUtils.getSqlSession()){
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            return commodityDao.getCommodityByName(name);
        }
    }

    public boolean addCommodity(Commodity commodity) {
        try(SqlSession session= MybatisUtils.getSqlSession()){
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            if (commodityDao.addCommodity(commodity)==1){
                session.commit();
                return true;
            }else {
                session.rollback();
                return false;
            }
        }
    }
}
