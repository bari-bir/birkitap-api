package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.common.UserVO;
import kz.baribir.birkitap.model.common.entity.Notification;
import kz.baribir.birkitap.service.NotificationService;
import kz.baribir.birkitap.service.UserService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import kz.baribir.birkitap.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String content = ParamUtil.get_string(params, "content", false);
            String toUserId = ParamUtil.get_string(params, "toUserId", false);
            String type = ParamUtil.get_string(params, "type", false);

            Notification notification = new Notification();
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            if (userService.get(toUserId) == null) {
                throw new RuntimeException("to User not found");
            }

            notification.setContent(content);
            notification.setCreatetime(System.currentTimeMillis());
            notification.setUserId(toUserId);
            notification.setType(type);
            notification.setFromUser(new UserVO().mapper(userService.get(tokenInfo.getUuid())));

            notificationService.create(notification);

            return Response.create_simple_success(notification);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/get")
    @ResponseBody
    public Response get(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(params, "id", false);



            return Response.create_simple_success( notificationService.get(id));
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/my/list")
    @ResponseBody
    public Response myList(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            Map<String, Object> filter = (Map<String, Object>) params.get("filter");
            if (filter == null) {
                filter = new HashMap<>();
            }
            filter.put("userId", tokenInfo.getUuid());
            params.put("filter", filter);

            return Response.create_simple_success( notificationService.list(params));
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/bookcorssing/create")
    @ResponseBody
    public Response bookcorssingCreate(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String content = ParamUtil.get_string(params, "content", false);
            String toUserId = ParamUtil.get_string(params, "toUserId", false);

            Notification notification = new Notification();
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            if (userService.get(toUserId) == null) {
                throw new RuntimeException("to User not found");
            }

            notification.setContent(content);
            notification.setCreatetime(System.currentTimeMillis());
            notification.setUserId(toUserId);
            notification.setType("bookcrossing");
            notification.setFromUser(new UserVO().mapper(userService.get(tokenInfo.getUuid())));

            notificationService.create(notification);

            return Response.create_simple_success(notification);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/bookcorssing/my/list")
    @ResponseBody
    public Response bookcorssingMyList(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            Map<String, Object> filter = (Map<String, Object>) params.get("filter");
            if (filter == null) {
                filter = new HashMap<>();
            }
            filter.put("userId", tokenInfo.getUuid());
            filter.put("type", "bookcrossing");
            params.put("filter", filter);

            return Response.create_simple_success( notificationService.list(params));
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }


}
