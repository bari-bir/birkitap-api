package kz.baribir.birkitap.algorithm;

import kz.baribir.birkitap.model.common.entity.Book;
import kz.baribir.birkitap.repository.BookRepository;
import kz.baribir.birkitap.repository.UserRepository;
import kz.baribir.birkitap.repository.questionnaire.AnswersRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecommendationAlgoByMBTII implements RecommendationAlgorithm<Book> {

    @Autowired
    private AnswersRepository answersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List execute(String userId) {

        return null;
    }
}
