package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.model.Response;
import kz.baribir.birkitap.model.dto.AnnouncementDTO;
import kz.baribir.birkitap.model.entity.User;
import kz.baribir.birkitap.service.UserService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping("/info")
    @ResponseBody
    public Response info(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            User user = userService.get(tokenInfo.getUuid());

            return Response.create_simple_success(user);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }
}
