package kz.baribir.birkitap.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AnnouncementDTO {
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

    public void validate() {
        checkNullOrEmpty(title, "title");
        checkNullOrEmpty(category, "category");
        checkNullOrEmpty(description, "description");
    }

    private void checkNullOrEmpty(String str, String paramName) {
        if (str == null) {
            throw new RuntimeException("%s is null".formatted(paramName));
        }


        if (str.trim().isEmpty()) {
            throw new RuntimeException("%s is empty".formatted(paramName));
        }
    }

}
