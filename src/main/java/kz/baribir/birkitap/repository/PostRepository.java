package kz.baribir.birkitap.repository;

import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.common.entity.Post;
import kz.baribir.birkitap.model.common.entity.Review;
import kz.baribir.birkitap.util.NoSqlUtil;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository  extends MongoRepositoryBase<Post> {



    public List<Post> list(Map<String, Object> param) {
        Map<String, String> param2column = new HashMap<>();
        param2column.put("id", "id");
        param2column.put("userId", "userId");
        param2column.put("content", "content");
        param2column.put("title", "title");
        param2column.put("createtime", "createtime");
        param2column.put("updatetime", "updatetime");
        Query query = NoSqlUtil.queryByParams(param, param2column, true);

        return find(query, Post.class);
    }

    public long count(Map<String, Object> param) {
        Map<String, String> param2column = new HashMap<>();
        param2column.put("id", "id");
        param2column.put("userId", "userId");
        param2column.put("content", "content");
        param2column.put("title", "title");
        param2column.put("createtime", "createtime");
        param2column.put("updatetime", "updatetime");
        Query query = NoSqlUtil.queryByParams(param, param2column, false);

        return count(query, Post.class);
    }

}
