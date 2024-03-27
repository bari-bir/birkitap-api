package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.Question;
import kz.baribir.birkitap.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question create(Question question) {
        questionRepository.save(question);
        return question;
    }

    @Override
    public Question get(String id) {
        Question question = questionRepository.findById(id, Question.class);

        return question;
    }

    @Override
    public Question update(Question question) {
        questionRepository.save(question);

        return question;
    }

    @Override
    public void delete(String id) {
        questionRepository.deleteById(id, Question.class);
    }

    @Override
    public List<Question> list(Map<String, Object> params) {
        return questionRepository.list(params);
    }
}
