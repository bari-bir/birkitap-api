package kz.baribir.birkitap.repository.booktracker;

import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.bookcrossing.entity.Favorite;
import kz.baribir.birkitap.model.booktracker.entity.BookTracker;
import kz.baribir.birkitap.util.NoSqlUtil;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookTrackerRepository extends MongoRepositoryBase<BookTracker> {

    public BookTracker findById(String id) {
        return findById(id, BookTracker.class);
    }

    public List<BookTracker> list(Map<String, Object> param) {
        Map<String, String> param2column = new HashMap<>();
        param2column.put("id", "id");
        param2column.put("status", "status");
        param2column.put("createtime", "createtime");
        param2column.put("updatetime", "updatetime");
        param2column.put("bookId", "book.id");

        Query query = NoSqlUtil.queryByParams(param, param2column, true);

        return find(query, BookTracker.class);
    }
}
