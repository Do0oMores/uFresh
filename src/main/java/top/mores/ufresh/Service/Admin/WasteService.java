package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.DAO.WasteDao;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.Commodity;
import top.mores.ufresh.POJO.Waste;

import java.util.List;

@Service
public class WasteService {

    /**
     * 获取商品信息
     *
     * @return 返回商品信息
     */
    public APIResponse<List<Commodity>> fetchWasteCommodities() {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            WasteDao wasteDao = session.getMapper(WasteDao.class);
            List<Commodity> data = wasteDao.getWasteCommodities();
            if (!data.isEmpty()) {
                return new APIResponse<>(200, data);
            } else {
                return new APIResponse<>(404, "没有商品信息");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生了意料之外的错误：" + e.getMessage());
        }
    }

    /**
     * 获取损耗记录
     *
     * @return 损耗记录
     */
    public APIResponse<List<Waste>> fetchWastes() {
        try (SqlSession session = MybatisUtils.getSqlSession()) {
            WasteDao wasteDao = session.getMapper(WasteDao.class);
            List<Waste> data = wasteDao.getAllWasteLog();
            if (!data.isEmpty()) {
                return new APIResponse<>(200, data);
            } else {
                return new APIResponse<>(404, "没有查询到损耗记录");
            }
        } catch (Exception e) {
            return new APIResponse<>(500, "发生了意料之外的错误：" + e.getMessage());
        }
    }
}
