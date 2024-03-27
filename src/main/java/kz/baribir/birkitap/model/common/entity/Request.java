package kz.baribir.birkitap.model.common.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Data
public class Request   {

    @MongoId
    private String id;
    private String creator;
    private List<String> attachments;
    private String message;
    private Date createtime;
    private String announcement;
    private String announcementCreator;
    private String status;
}
