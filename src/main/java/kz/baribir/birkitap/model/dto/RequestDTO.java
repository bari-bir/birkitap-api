package kz.baribir.birkitap.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RequestDTO {
    private String id;
    private String creator;
    private String message;
    private List<String> attachments;
    private Date createtime;
    private String announcement;
    private String announcementCreator;
    private String status;

    public void validate() {
        checkNullOrEmpty(creator, "creator");
        checkNullOrEmpty(announcement, "announcement");
        checkNullOrEmpty(message, "message");
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
