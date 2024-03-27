package kz.baribir.birkitap.model.common.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class BookCategory {

    @MongoId
    private String id;
    private String title;
    private String icon;
    private String url;
    private int visible;
    private int sort;
}
