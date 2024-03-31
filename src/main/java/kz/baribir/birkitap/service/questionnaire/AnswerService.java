package kz.baribir.birkitap.service.questionnaire;

import kz.baribir.birkitap.model.questionnaire.entity.Answer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AnswerService {

    Answer create(Answer answer);
    Answer get(String id);
    Answer update(Answer answer);
    void delete(String id);
    List<Answer> list(Map<String, Object> params);
}
