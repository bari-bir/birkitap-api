package kz.baribir.birkitap.model.common.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
public class Review {

    @MongoId
    private String id;
    private String userId;
    private String userName;
    private String bookId;
    private String title;
    private String message;
    private int rating;
    private long createtime;
    private long updatetime;
}
