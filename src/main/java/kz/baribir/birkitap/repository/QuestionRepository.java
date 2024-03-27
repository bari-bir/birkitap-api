package kz.baribir.birkitap.repository;

import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.common.entity.Question;
import kz.baribir.birkitap.model.common.entity.Review;
import kz.baribir.birkitap.util.NoSqlUtil;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuestionRepository extends MongoRepositoryBase<Question> {

    public List<Question> list(Map<String, Object> param) {
        Map<String, String> param2column = new HashMap<>();
        param2column.put("id", "id");
        param2column.put("question", "question");
        param2column.put("type", "type");
        param2column.put("author", "author");
        Query query = NoSqlUtil.queryByParams(param, param2column, true);

        return find(query, Question.class);
    }

}
