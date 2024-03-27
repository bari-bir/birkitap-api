package kz.baribir.birkitap.controller.bookcrossing;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.bookcrossing.vo.RequestVO;
import kz.baribir.birkitap.model.common.UserVO;
import kz.baribir.birkitap.model.common.dto.AnnouncementDTO;
import kz.baribir.birkitap.model.common.dto.RequestDTO;
import kz.baribir.birkitap.model.common.entity.User;
import kz.baribir.birkitap.service.UserService;
import kz.baribir.birkitap.service.bookcrossing.AnnouncementService;
import kz.baribir.birkitap.service.bookcrossing.RequestService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/bookcrossing/request")
@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody RequestDTO requestDTO, HttpServletRequest request) {
        try {

            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);


            requestDTO.setCreatetime(new Date());
            requestDTO.setStatus("created");
            requestDTO.setCreator(tokenInfo.getUuid());
            requestDTO.validate();
            AnnouncementDTO announcementDTO = announcementService.get(requestDTO.getAnnouncement());
            requestDTO.setAnnouncementCreator(announcementDTO.getCreator());
            return Response.create_simple_success(requestService.create(requestDTO));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/get")
    @ResponseBody
    public Response get(@RequestParam String id, HttpServletRequest request) {
        try {
            if (id == null) {
                throw new RuntimeException("id is null");
            }

            return Response.create_simple_success(requestService.get(id));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Response update(@RequestBody RequestDTO requestDTO, HttpServletRequest request) {
        try {
            requestDTO.validate();

            return Response.create_simple_success(requestDTO);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Response delete(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (id == null) {
                throw new RuntimeException("id is null");
            }
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            RequestDTO requestDTO = requestService.get(id);
            if (tokenInfo.getUuid().equals(requestDTO.getCreator())) {
                response.setStatus(403);
                throw new RuntimeException("Not allowed delete!");
            }

            requestService.delete(id);
            return Response.create_simple_success();
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            List<RequestVO> result = new ArrayList<>();
            List<RequestDTO> requestDTOS = requestService.list(param);
            for (var requestDTO: requestDTOS) {
                var vo = requestDTO.mapper();
                try {
                    vo.setAnnouncement(announcementService.get(requestDTO.getAnnouncement()));
                    result.add(vo);
                } catch (Exception e) {

                }
            }

            return Response.create_simple_success(result);
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

            List<RequestVO> result = new ArrayList<>();
            List<RequestDTO> requestDTOS = requestService.list(param);
            for (var requestDTO: requestDTOS) {
                var vo = requestDTO.mapper();
                try {
                    vo.setAnnouncement(announcementService.get(requestDTO.getAnnouncement()));
                    result.add(vo);
                } catch (Exception e) {

                }
            }

            return Response.create_simple_success(result);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @Autowired
    private UserService userService;

    @RequestMapping("/me/list")
    @ResponseBody
    public Response meList(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            Map<String, Object> filter = (Map<String, Object>) param.get("filter");
            if (filter == null) {
                filter = new HashMap<>();
            }
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            filter.put("announcementCreator", tokenInfo.getUuid());
            param.put("filter", filter);


            List<RequestVO> result = new ArrayList<>();
            List<RequestDTO> requestDTOS = requestService.list(param);
            for (var requestDTO: requestDTOS) {
                var vo = requestDTO.mapper();
                try {
                    vo.setAnnouncement(announcementService.get(requestDTO.getAnnouncement()));
                    User user = userService.get(vo.getCreator());
                    vo.setUserInfo(new UserVO().mapper(user));
                    result.add(vo);
                } catch (Exception e) {

                }
            }

            return Response.create_simple_success(result);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }
}

