package kz.baribir.birkitap.model.common.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
public class Question {

    @MongoId
    private String id;
    private String question;
    private List<String> answers;
    private String type;
    private String author;
}
