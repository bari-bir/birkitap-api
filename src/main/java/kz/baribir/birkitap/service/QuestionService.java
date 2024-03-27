package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.Question;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    Question create(Question question);
    Question get(String id);
    Question update(Question question);
    void delete(String id);
    List<Question> list(Map<String, Object> params);
}
