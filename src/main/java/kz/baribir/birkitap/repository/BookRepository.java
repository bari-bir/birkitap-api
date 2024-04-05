package kz.baribir.birkitap.repository;

import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.common.entity.Book;
import kz.baribir.birkitap.util.NoSqlUtil;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepository extends MongoRepositoryBase<Book> {

    public List<Book> list(Map<String, Object> param) {
        Map<String, String> param2column = new HashMap<>();
        param2column.put("id", "id");
        param2column.put("title", "title");
        param2column.put("author", "author");
        param2column.put("year", "year");
        param2column.put("genre", "genres");
        Query query = NoSqlUtil.queryByParams(param, param2column, true);
        Map<String, Object> filter = (Map<String, Object>) param.get("filter");
        if (filter == null)
            filter = new HashMap<>();
        List<String> genres = (List<String>)filter.get("genres");
        if (genres != null && !genres.isEmpty()) {
            query.addCriteria(Criteria.where("genres").in(genres));
        }

        return find(query, Book.class);
    }
}
