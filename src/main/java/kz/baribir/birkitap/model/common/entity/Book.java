package kz.baribir.birkitap.model.common.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
public class Book {

    @MongoId
    private String id;
    private String title;
    private String author;
    private String imageLink;
    private List<String> genres;
    private float rating;
    private int reviewCount;
    private int ratingSum;
    private List<String> collections;
    private int year;
    private String description;
    private int page;
    private int visible = 1;
}
