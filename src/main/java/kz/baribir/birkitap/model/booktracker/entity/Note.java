package kz.baribir.birkitap.model.booktracker.entity;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;
import kz.baribir.birkitap.model.common.entity.Book;

import java.util.Date;

@Data
public class Note {
    @MongoId
    private String id;
    private String userId;
    private String title;
    private String bookId;
    private String content;
    private Date createtime;
    private Date updatetime;
    private Book book;
}
