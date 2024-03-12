package kz.baribir.birkitap.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Genre {

    @MongoId
    private String id;
    private String title;
}
