package kz.baribir.birkitap.model.questionnaire.entity;

import lombok.Data;

import java.util.List;

@Data
public class MBTIQuestion extends Question<Integer> {

    public MBTIQuestion() {
        List<Integer> answers = List.of(-2, -1, 0, 1, 2);
        super.setAnswers(answers);
    }
}
