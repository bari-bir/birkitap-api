package kz.baribir.birkitap.model.bookcrossing.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Favorite {

    @MongoId
    private String id;
    private String announcement;
    private String creator;

}
