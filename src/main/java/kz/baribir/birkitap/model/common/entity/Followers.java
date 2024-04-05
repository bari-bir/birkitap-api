package kz.baribir.birkitap.model.common.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Followers {

    @MongoId
    private String id;
    private String toUserId;
    private String fromUserId;

}
