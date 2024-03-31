package kz.baribir.birkitap.model.questionnaire.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Map;

@Data
public class Answer {

    @MongoId
    private String id;
    private QuestionTest question;
    private List<Map<String, Object>> answers;
    private long createtime;
}
