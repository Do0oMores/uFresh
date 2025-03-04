package top.mores.ufresh.DAO;

import top.mores.ufresh.POJO.Old_order_items;

import java.util.List;

public interface OldOrderItemsDao {
    List<Old_order_items> getOldOrderItems(int user_id);
}
