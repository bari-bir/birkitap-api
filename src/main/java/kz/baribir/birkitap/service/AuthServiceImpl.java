package kz.baribir.birkitap.service;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.common.entity.User;
import kz.baribir.birkitap.repository.UserRepository;
import kz.baribir.birkitap.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${user.refreshTokenSecret}")
    private String refreshTokenSecret;
    @Value("${jwt.token_expire_second}")
    private int tokenExpireSecond;

    @Value("${jwt.refresh-token-expire-second}")
    private int refreshTokenExpireSecond;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public Response login(Map<String, Object> params, HttpServletRequest request) {
        String username = ParamUtil.get_string(params, "username", false);
        String password = ParamUtil.get_string(params, "password", false);

        if (username.isEmpty() || password.isEmpty()) {
            throw new RuntimeException("username or password empty!");
        }

        User user = checkUser(username, password);
        if (user == null) {
            throw new RuntimeException("username or password incorrect!");
        }
        String refresh_token = generateRefreshToken(user.getId());

        refresh_token = SecurityBase64Util.md5(refresh_token);

        String token = jwtUtils.getToken(Map.of("uid", user.getId()));
        user.setLastLogin(new Date());
        user.setRefreshToken(refresh_token);
        userRepository.save(user);

        return new Response(0, "", Map.of(
                "id", user.getId(),
                "token", token,
                "refreshToken", refresh_token,
                "userInfo", user,
                "tokenExpireToken", tokenExpireSecond
        ));
    }

    private User checkUser(String username, String password) {
        User user = null;

        if (isValidPhone(username)) {
            user = userRepository.findOneByPhone(username);
        } else {
            user = userRepository.findOneByEmail(username);
        }

        if (checkPassword(user, password)) {
            return user;
        }

        return null;
    }

    private boolean isValidPhone(String username) {
        if (username.startsWith("+7") || username.startsWith("8")) {
            return true;
        }

        return false;
    }

    private boolean checkPassword(User user, String password) {
        String sha256passwordHashed= PojUtil.sha256password_hash(password);
        return sha256passwordHashed.equals(user.getPassword());
    }

    private String generateRefreshToken(String id) {
        String randString = PojUtil.random_uuid();
        String signRefreshToken = SecurityBase64Util.md5(id +randString+refreshTokenSecret);
        String newRefreshToken = id + " " + signRefreshToken + " " + randString;

        return newRefreshToken;
    }


    @Override
    public Response register(Map<String, Object> params, HttpServletRequest request) {
        String phone = ParamUtil.get_string(params, "phone", false);
        String email = ParamUtil.get_string(params, "email", false);
        String fullName = ParamUtil.get_string(params, "fullName", false);
        String password = ParamUtil.get_string(params, "password", false);

        Long birth = ParamUtil.get_long(params, "birth", false, 0L);
        String gender = ParamUtil.get_string(params, "gender", false);

        //TODO verify with OTP or email

        User user = new User();
        user.setCreatetime(new Date());
        user.setEmail(email);
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setLastLogin(new Date());
        user.setPassword(SecurityBase64Util.sha256password_hash(password));
        user.setBirth(new Date(birth));
        user.setGender(gender);
        user.setAvatar(getRandomAvatar());
        String refresh_token = generateRefreshToken(user.getId());
        refresh_token = SecurityBase64Util.md5(refresh_token);
        user.setRefreshToken(refresh_token);

        userRepository.save(user);

        String token = jwtUtils.getToken(Map.of("uid", user.getId()));

        return new Response(0, "", Map.of(
                "id", user.getId(),
                "token", token,
                "refreshToken", refresh_token,
                "userInfo", user,
                "tokenExpireToken", tokenExpireSecond
        ));
    }

    private String getRandomAvatar() {
        String url = "public/avatar/";
        long time = System.currentTimeMillis()/1000;
        int n = (int) (time) % 4;
        switch (n) {
            case 0 -> {
                return  url + "logo1.png";
            }
            case 1 -> {
                return  url + "logo2.png";
            }
            case 2-> {
                return  url + "logo3.png";
            }
            case 3-> {
                return  url + "logo4.png";
            }
            default-> {
                return  url + "logo5.png";
            }
        }
    }

    @Override
    public Response updatePassword(Map<String, Object> params, HttpServletRequest request) {

        return null;
    }

    @Override
    public Response resetPassword(Map<String, Object> params, HttpServletRequest request) {
        return null;
    }

    @Override
    public Response refreshToken(Map<String, Object> params, HttpServletRequest request) {
        String refreshToken = ParamUtil.get_string(params, "refreshToken", false);
        User user = userRepository.findOneByRefreshToken(refreshToken);
        if (user != null) {
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(user.getLastLogin());
            calendar.add(Calendar.SECOND, refreshTokenExpireSecond);
            Date end_time=calendar.getTime();//refresh token end time
            Date cur_time=new Date();
            if(cur_time.compareTo(end_time)<0)
            {
                String token=jwtUtils.getToken(Map.of("uid",user.getId()));
                return new Response(0, "OK", Map.of("uid",user.getId(),"token",token,"refreshToken",refreshToken,"refreshTokenExpireSecond",refreshTokenExpireSecond));
            }
        }

        return Response.create_simple_error();
    }
}
