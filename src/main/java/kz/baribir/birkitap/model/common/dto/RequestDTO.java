package kz.baribir.birkitap.model.common.dto;

import kz.baribir.birkitap.model.bookcrossing.vo.RequestVO;
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

    public RequestVO mapper() {
        RequestVO requestVO = new RequestVO();
        requestVO.setId(this.getId());
        requestVO.setCreator(this.getCreator());
        requestVO.setMessage(this.getMessage());
        requestVO.setAttachments(this.getAttachments());
        requestVO.setCreatetime(this.getCreatetime().getTime());
        requestVO.setAnnouncementId(this.getAnnouncement());
        requestVO.setAnnouncementCreator(this.getAnnouncementCreator());
        requestVO.setStatus(this.status);

        return requestVO;
    }

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
