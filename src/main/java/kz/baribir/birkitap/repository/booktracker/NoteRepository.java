package kz.baribir.birkitap.repository.booktracker;

import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.booktracker.entity.Note;
import kz.baribir.birkitap.util.NoSqlUtil;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class NoteRepository extends MongoRepositoryBase<Note> {

    public Note findById(String id) {
        return findById(id, Note.class);
    }

    public List<Note> list(Map<String, Object> param) {
        Map<String, String> param2column = new HashMap<>();
        param2column.put("id", "id");
        param2column.put("status", "status");
        param2column.put("createtime", "createtime");
        param2column.put("updatetime", "updatetime");
        Query query = NoSqlUtil.queryByParams(param, param2column, true);

        return find(query, Note.class);
    }

    public void deleteById(String id) {
       deleteById(id, Note.class);
    }
}
