package kz.baribir.birkitap.service;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.model.Response;
import kz.baribir.birkitap.model.entity.User;
import kz.baribir.birkitap.repository.UserRepository;
import kz.baribir.birkitap.util.JWTUtils;
import kz.baribir.birkitap.util.ParamUtil;
import kz.baribir.birkitap.util.PojUtil;
import kz.baribir.birkitap.util.SecurityBase64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${user.refreshTokenSecret}")
    private String refreshTokenSecret;
    @Value("${jwt.token_expire_second}")
    private int tokenExpireSecond;

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

        String token = jwtUtils.getToken(Map.of("uid", user.getId()));
        user.setLastLogin(new Date());
        user.setRefreshToken(SecurityBase64Util.md5(refresh_token));

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
        String refresh_token = generateRefreshToken(user.getId());

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
        return null;
    }
}