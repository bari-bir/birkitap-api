package kz.baribir.birkitap.model.common.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Data
public class User {

    @MongoId
    private String id;
    private String email;
    private String phone;
    private String fullName;
    private String password;
    private Date birth;
    private String gender;
    private List<Genre> genres;
    private Date createtime;
    private String refreshToken;
    private Date lastLogin;
    private String avatar;

}
