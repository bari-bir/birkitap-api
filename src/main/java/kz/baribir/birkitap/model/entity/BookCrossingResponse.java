package kz.baribir.birkitap.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Data
public class BookCrossingResponse {

    @MongoId
    private String id;
    private String userId;
    private String toUserId;
    private String crossingRequestId;
    private String description;
    private List<String> attachments;
    private String status;
    private Date createtime;

}
