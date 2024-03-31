package kz.baribir.birkitap.service.questionnaire;

import kz.baribir.birkitap.model.questionnaire.entity.QuestionTest;

import java.util.List;
import java.util.Map;

public interface QuestionTestService {

    QuestionTest create(QuestionTest questionTest);
    QuestionTest get(String id);
    QuestionTest update(QuestionTest questionTest);
    void delete(String id);
    List<QuestionTest> list(Map<String, Object> params);
}
