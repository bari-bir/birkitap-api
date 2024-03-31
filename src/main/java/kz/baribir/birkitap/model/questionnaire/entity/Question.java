package kz.baribir.birkitap.model.questionnaire.entity;

import lombok.Data;

import java.util.List;

@Data
public abstract class Question<T> {

    private String id;
    private String question;
    private List<T> answers;
}
