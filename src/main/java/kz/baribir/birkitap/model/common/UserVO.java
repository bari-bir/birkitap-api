package kz.baribir.birkitap.model.common;

import kz.baribir.birkitap.model.common.entity.Genre;
import kz.baribir.birkitap.model.common.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserVO {

    private String email;
    private String phone;
    private String fullName;
    private long birth;
    private String gender;
    private String avatar;
    private List<Genre> genres;
    private long createtime;
    private String refreshToken;
    private long lastLogin;

    public UserVO mapper(User user) {
        email = user.getEmail();
        phone = user.getPhone();
        fullName = user.getFullName();
        birth = user.getBirth().getTime();
        gender = user.getGender();
        genres = user.getGenres();
        createtime = user.getCreatetime().getTime();
        refreshToken = user.getRefreshToken();
        lastLogin = user.getLastLogin().getTime();
        avatar = user.getAvatar();

        return this;
    }
}
