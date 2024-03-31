package kz.baribir.birkitap.model.questionnaire.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionTest {

    @MongoId
    private String id;
    private String title;
    private List<Question> questions = new ArrayList<>();
    private String type;
    private int visible = 1;
}
