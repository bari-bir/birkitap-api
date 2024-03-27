package kz.baribir.birkitap.model.common.entity;

import kz.baribir.birkitap.model.common.UserVO;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Notification {

    @MongoId
    private String id;
    private String content;
    private String userId;
    private UserVO fromUser;
    private long createtime;
    private String type;

}
