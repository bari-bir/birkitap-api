package kz.baribir.birkitap.service.questionnaire;

import kz.baribir.birkitap.model.questionnaire.entity.QuestionTest;
import kz.baribir.birkitap.repository.questionnaire.QuestionTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuestionTestServiceImpl implements QuestionTestService{

    @Autowired
    private QuestionTestRepository questionTestRepository;

    @Override
    public QuestionTest create(QuestionTest questionTest) {
        questionTestRepository.save(questionTest);
        return questionTest;
    }

    @Override
    public QuestionTest get(String id) {
        QuestionTest q = questionTestRepository.findById(id, QuestionTest.class);
        if (q == null) {
            throw new RuntimeException("not found questionTest");
        }
        return q;
    }

    @Override
    public QuestionTest update(QuestionTest questionTest) {
        questionTestRepository.save(questionTest);
        return questionTest;
    }

    @Override
    public void delete(String id) {
        questionTestRepository.deleteById(id, QuestionTest.class);
    }

    @Override
    public List<QuestionTest> list(Map<String, Object> params) {
        return null;
    }
}
