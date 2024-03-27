package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.common.UserVO;
import kz.baribir.birkitap.model.common.entity.Genre;
import kz.baribir.birkitap.model.common.entity.User;
import kz.baribir.birkitap.service.UserService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
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

            return Response.create_simple_success(new UserVO().mapper(user));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/genre/add")
    @ResponseBody
    public Response addGenre(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            List<Genre> genres = (List<Genre>) params.getOrDefault("genres", new ArrayList<>());

            User user = userService.get(tokenInfo.getUuid());
            user.setGenres(genres);
            userService.update(user);
            return Response.create_simple_success();
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }
}
