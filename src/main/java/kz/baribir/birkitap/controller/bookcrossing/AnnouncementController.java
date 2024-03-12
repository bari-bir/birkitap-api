package kz.baribir.birkitap.controller.bookcrossing;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.model.Response;
import kz.baribir.birkitap.model.dto.AnnouncementDTO;
import kz.baribir.birkitap.service.bookcrossing.AnnouncementService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bookcrossing/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody AnnouncementDTO announcement, HttpServletRequest request) {
        try {
            announcement.validate();

            announcement.setCreatetime(new Date());
            announcement.setUpdatetime(new Date());
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            announcement.setCreator(tokenInfo.getUuid());
            return Response.create_simple_success(announcementService.create(announcement));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @GetMapping("/get")
    @ResponseBody
    public Response get(@RequestParam String id, HttpServletRequest request) {
        try {
            if (id == null) {
                throw new RuntimeException("id is null");
            }

            return Response.create_simple_success(announcementService.get(id));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Response update(@RequestBody AnnouncementDTO announcementDTO, HttpServletRequest request) {
        try {
            announcementDTO.validate();

            return Response.create_simple_success(announcementService.update(announcementDTO));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public Response delete(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (id == null) {
                throw new RuntimeException("id is null");
            }
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            AnnouncementDTO announcementDTO = announcementService.get(id);
            if (tokenInfo.getUuid().equals(announcementDTO.getCreator())) {
                response.setStatus(403);
                throw new RuntimeException("Not allowed delete!");
            }

            announcementService.delete(id);
            return Response.create_simple_success();
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            return Response.create_simple_success(announcementService.list(param));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/my/list")
    @ResponseBody
    public Response myList(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            Map<String, Object> filter = (Map<String, Object>) param.get("filter");
            if (filter == null) {
                filter = new HashMap<>();
            }
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            filter.put("creator", tokenInfo.getUuid());
            param.put("filter", filter);
            return Response.create_simple_success(announcementService.list(param));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

}
