package kz.baribir.birkitap.model.common.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Data
public class Post {

    @MongoId
    private String id;
    private List<String> attachments = new ArrayList<>();
    private String title;
    private String content;
    private long createtime;
    private String userId;
    private long updatetime;
}
