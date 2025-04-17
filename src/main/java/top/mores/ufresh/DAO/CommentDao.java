package top.mores.ufresh.DAO;

import org.springframework.stereotype.Repository;
import top.mores.ufresh.POJO.Comment;

import java.util.List;

@Repository
public interface CommentDao {

    List<Comment> getCommentByCommodityID(int commodity_id);

    int addComment(Comment comment);
}
