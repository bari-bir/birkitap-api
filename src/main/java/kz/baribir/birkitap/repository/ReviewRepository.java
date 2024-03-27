package kz.baribir.birkitap.repository;

import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.common.entity.Review;
import kz.baribir.birkitap.util.NoSqlUtil;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewRepository extends MongoRepositoryBase<Review>  {

    public List<Review> list(Map<String, Object> param) {
        Map<String, String> param2column = new HashMap<>();
        param2column.put("id", "id");
        param2column.put("userId", "userId");
        param2column.put("bookId", "bookId");
        param2column.put("createtime", "createtime");
        param2column.put("updatetime", "updatetime");
        Query query = NoSqlUtil.queryByParams(param, param2column, true);

        return find(query, Review.class);
    }
}
