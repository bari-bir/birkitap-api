package kz.baribir.birkitap.controller.bookcrossing;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.model.Response;
import kz.baribir.birkitap.model.bookcrossing.entity.Favorite;
import kz.baribir.birkitap.service.bookcrossing.FavoriteService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import kz.baribir.birkitap.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/bookcrossing/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            String announcement = ParamUtil.get_string(param, "announcementId", false);
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            Favorite favorite = new Favorite();
            favorite.setCreator(tokenInfo.getUuid());
            favorite.setAnnouncement(announcement);

            return Response.create_simple_success(favoriteService.create(favorite));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Response delete(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            String favoriteId = ParamUtil.get_string(param, "favoriteId", false);
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            favoriteService.delete(favoriteId);

            return Response.create_simple_success();
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            return Response.create_simple_success(favoriteService.list(tokenInfo.getUuid()));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }
}
