package kz.baribir.birkitap.model.common.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class News {

    @MongoId
    private String id;
    private String title;
    private String content;
    private String imageLink;
    private String verticalImageLink;
    private long createtime;
}
