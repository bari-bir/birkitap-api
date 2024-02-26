package kz.baribir.birkitap.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

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

    private Date createtime;
    private String refreshToken;
    private Date lastLogin;

}
