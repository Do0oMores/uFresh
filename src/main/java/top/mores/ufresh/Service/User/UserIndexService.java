package top.mores.ufresh.Service.User;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.CommodityDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.UserDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;

import java.util.List;

@Service
public class UserIndexService {

    /**
     * 同步用户导航栏头像图片
     *
     * @param userId 传入用户ID
     * @return 图片url
     */
    public String syncAvatarImage(Integer userId) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            UserDao userDao = session.getMapper(UserDao.class);
            return userDao.syncAvatar(userId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回已上架商品信息
     *
     * @return 已上架商品信息
     */
    public APIResponse<List<Commodity>> fetchCommodities() {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            List<Commodity> commodities = commodityDao.fetchUserIndexCommodity("已上架");
            return new APIResponse<>(200, commodities);
        } catch (Exception e) {
            return new APIResponse<>(500, "发生错误：" + e.getMessage());
        }
    }

    /**
     * 根据商品名与商品类型查询商品
     *
     * @param commodity 商品名与商品类型
     * @return 查询的商品结果
     */
    public APIResponse<List<Commodity>> searchCommodities(Commodity commodity) {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            CommodityDao commodityDao = session.getMapper(CommodityDao.class);
            commodity.setStatus("已上架");
            List<Commodity> commodities = commodityDao.searchCommodity(commodity);
            if (commodities.isEmpty()) {
                return new APIResponse<>(404, "没有查询到对应的商品");
            } else {
                return new APIResponse<>(200, commodities);
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生意料之外的错误：" + e.getMessage());
        }
    }
}
