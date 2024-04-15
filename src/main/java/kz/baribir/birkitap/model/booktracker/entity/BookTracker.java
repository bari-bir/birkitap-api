package kz.baribir.birkitap.model.booktracker.entity;

import kz.baribir.birkitap.model.common.entity.Book;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
public class BookTracker {

    @MongoId
    private String id;
    private String userId;
    private String title;
    private Book book;
    private String image;
    private String status;
    private long createtime;
    private long updatetime;
    private long time;
    private int progressPage;
    private int page;

}
