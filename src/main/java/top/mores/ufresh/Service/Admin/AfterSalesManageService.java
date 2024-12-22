package top.mores.ufresh.Service.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import top.mores.ufresh.DAO.AfterSalesDao;
import top.mores.ufresh.DAO.MybatisUtils;
import top.mores.ufresh.POJO.APIResponse;
import top.mores.ufresh.POJO.After_sales;

import java.util.List;

@Service
public class AfterSalesManageService {

    public APIResponse<List<After_sales>> getAfterSales(){
        try(SqlSession session= MybatisUtils.getSqlSession()){
            AfterSalesDao afterSalesDao = session.getMapper(AfterSalesDao.class);
            List<After_sales> afterSales=afterSalesDao.getAfterSalesList();
            return new APIResponse<>(200,afterSales);
        }catch(Exception e){
            return new APIResponse<>(500,"发生错误："+e.getMessage());
        }
    }
}
