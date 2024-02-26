package kz.baribir.birkitap.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Data
public class BookCrossingRequest {

    @MongoId
    private String id;
    private String userId;
    private String title;
    private String city;
    private String year;
    private String description;
    private List<String> images;
    private String status;
    private Date createtime;
    private Date updatetime;
}
