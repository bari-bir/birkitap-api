package kz.baribir.birkitap.service.questionnaire;

import kz.baribir.birkitap.model.questionnaire.entity.Answer;
import kz.baribir.birkitap.repository.questionnaire.AnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AnswerServiceImpl implements AnswerService{

    @Autowired
    private AnswersRepository answersRepository;

    @Override
    public Answer create(Answer answer) {
        answersRepository.save(answer);
        return answer;
    }

    @Override
    public Answer get(String id) {
        Answer answer = answersRepository.findById(id, Answer.class);
        if (answer == null) {
            throw new RuntimeException("answers not found");
        }

        return answer;
    }

    @Override
    public Answer update(Answer answer) {
        answersRepository.save(answer);
        return answer;
    }

    @Override
    public void delete(String id) {
        answersRepository.deleteById(id, Answer.class);
    }

    @Override
    public List<Answer> list(Map<String, Object> params) {
        return null;
    }
}
