package kz.baribir.birkitap.model.bookcrossing.vo;

import kz.baribir.birkitap.model.common.UserVO;
import kz.baribir.birkitap.model.common.dto.AnnouncementDTO;
import kz.baribir.birkitap.model.common.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class RequestVO {
    private String id;
    private String creator;
    private String message;
    private List<String> attachments;
    private long createtime;
    private String announcementId;
    private AnnouncementDTO announcement;
    private String announcementCreator;
    private String status;
    private UserVO userInfo;
}
