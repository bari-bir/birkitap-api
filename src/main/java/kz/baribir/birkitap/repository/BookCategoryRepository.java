package kz.baribir.birkitap.repository;

import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.common.entity.Book;
import kz.baribir.birkitap.model.common.entity.BookCategory;
import kz.baribir.birkitap.util.NoSqlUtil;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookCategoryRepository extends MongoRepositoryBase<BookCategory> {

    public List<BookCategory> list(Map<String, Object> param) {
        Map<String, String> param2column = new HashMap<>();
        param2column.put("id", "id");
        param2column.put("title", "title");
        param2column.put("icon", "icon");
        param2column.put("url", "url");
        Query query = NoSqlUtil.queryByParams(param, param2column, true);

        return find(query, BookCategory.class);
    }

    public long count(Map<String, Object> param) {
        Map<String, String> param2column = new HashMap<>();
        param2column.put("id", "id");
        param2column.put("title", "title");
        param2column.put("icon", "icon");
        param2column.put("url", "url");
        Query query = NoSqlUtil.queryByParams(param, param2column, true);

        return mongoTemplate.count(query, BookCategory.class);
    }

}
