package kz.baribir.birkitap.controller.bookcrossing;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.baribir.birkitap.bean.SaveResult;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.components.UploadDownloadUtil;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.bookcrossing.vo.AnnouncementVO;
import kz.baribir.birkitap.model.bookcrossing.entity.Favorite;
import kz.baribir.birkitap.model.common.dto.AnnouncementDTO;
import kz.baribir.birkitap.service.bookcrossing.AnnouncementService;
import kz.baribir.birkitap.service.bookcrossing.FavoriteService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
            var vo = announcementService.get(id).mapper();
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            var list = favoriteService.list(tokenInfo.getUuid());
            for (var fav : list) {
                if (fav.getAnnouncement().equals(vo.getId())) {
                    vo.setFavorite(true);
                    vo.setFavoriteId(fav.getId());
                }
            }
            return Response.create_simple_success(vo);
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
            if (!tokenInfo.getUuid().equals(announcementDTO.getCreator())) {
                response.setStatus(403);
                throw new RuntimeException("Not allowed delete!");
            }

            announcementService.delete(id);
            return Response.create_simple_success();
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @Autowired
    private FavoriteService favoriteService;

    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            List<Favorite> favoriteList = favoriteService.list(tokenInfo.getUuid());
            Map<String, String> favoriteMap = new HashMap<>();
            for (var favorite : favoriteList) {
                favoriteMap.put(favorite.getAnnouncement(), favorite.getId());
            }
            List<AnnouncementVO> result = new ArrayList<>();
            List<AnnouncementDTO> announcementDTOS = announcementService.list(param);
            for (var announcement : announcementDTOS) {
                announcement.setFavorite(favoriteMap.containsKey(announcement.getId()));
                if (announcement.isFavorite()) {
                    announcement.setFavoriteId(favoriteMap.get(announcement.getId()));
                }
                result.add(announcement.mapper());
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
            var list = announcementService.list(param);
            List<AnnouncementVO> result = new ArrayList<>();
            for (var announcement : list) {
                result.add(announcement.mapper());
            }

            return Response.create_simple_success(result);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @Autowired
    private UploadDownloadUtil uploadDownloadUtil;

    @PostMapping("/upload")
    @ResponseBody
    public Response upload_file(@RequestParam("file") MultipartFile file, HttpServletRequest request)
    {
        try {
            SaveResult saveResult = uploadDownloadUtil.save_multipart(file, "public/announcement", request);
            if (saveResult.isSuccess()) {
                String up_path = saveResult.getFileId();
                return new Response(0, "OK", Map.of("path", up_path));
            }
            return new Response(-2, saveResult.getErrMsg(), null);
        }catch (Exception e)
        {
            return new Response(-2,e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/favorites")
    @ResponseBody
    public Response favorites(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            List<Favorite> favoriteList = favoriteService.list(tokenInfo.getUuid());
            Map<String, String> favoriteMap = new HashMap<>();
            for (var favorite : favoriteList) {
                favoriteMap.put(favorite.getAnnouncement(), favorite.getId());
            }

            List<AnnouncementVO> result = new ArrayList<>();
            List<AnnouncementDTO> announcementDTOS = announcementService.list(params);
            for (var announcement : announcementDTOS) {
                announcement.setFavorite(favoriteMap.containsKey(announcement.getId()));
                if (announcement.isFavorite()) {
                    announcement.setFavoriteId(favoriteMap.get(announcement.getId()));
                    result.add(announcement.mapper());
                }
            }

            return Response.create_simple_success(result);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

}
