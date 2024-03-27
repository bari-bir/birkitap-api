package kz.baribir.birkitap.model.common.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Data
public class Announcement  {

    @MongoId
    private String id;
    private String title;
    private String category;
    private String location;
    private int year;
    private List<String> images;
    private String description;
    private String creator;
    private Date createtime;
    private Date updatetime;
    private int deleted;

}
