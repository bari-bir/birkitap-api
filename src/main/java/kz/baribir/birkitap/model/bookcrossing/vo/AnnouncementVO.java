package kz.baribir.birkitap.model.bookcrossing.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AnnouncementVO {
    private String id;
    private String title;
    private String category;
    private String location;
    private int year;
    private List<String> images;
    private String description;
    private String creator;
    private long createtime;
    private long updatetime;
    private boolean isFavorite;
    private String favoriteId;
    private int deleted;
}
