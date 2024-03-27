package kz.baribir.birkitap.controller.booktracker;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.booktracker.entity.Note;
import kz.baribir.birkitap.service.booktracker.NoteService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import kz.baribir.birkitap.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private NoteService noteService;

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            String title = ParamUtil.get_string(params, "title", true);
            String content = ParamUtil.get_string(params, "content", false);
            String bookId = ParamUtil.get_string(params, "bookId", false);

            Note note = new Note();
            note.setUserId(tokenInfo.getUuid());
            note.setTitle(title);
            note.setBookId(bookId);
            note.setContent(content);
            note.setCreatetime(new Date());
            note.setUpdatetime(new Date());
            noteService.create(note);

            return Response.create_simple_success(note);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/get")
    @ResponseBody
    public Response get(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            String id = ParamUtil.get_string(params, "id", false);

            Note note = noteService.get(id);

            if (!note.getUserId().equals(tokenInfo.getUuid())) {
                throw new RuntimeException("No access");
            }

            return Response.create_simple_success(note);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Response update(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            String id = ParamUtil.get_string(params, "id", false);
            String title = ParamUtil.get_string(params, "title", true);
            String content = ParamUtil.get_string(params, "content", false);
            String bookId = ParamUtil.get_string(params, "bookId", false);

            Note note = noteService.get(id);

            if (!note.getUserId().equals(tokenInfo.getUuid())) {
                throw new RuntimeException("No access");
            }

            note.setTitle(title);
            note.setBookId(bookId);
            note.setContent(content);
            note.setCreatetime(new Date());
            note.setUpdatetime(new Date());
            noteService.update(note);

            return Response.create_simple_success(note);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Response delete(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            String id = ParamUtil.get_string(params, "id", false);

            Note note = noteService.get(id);

            noteService.delete(note.getId());

            if (!note.getUserId().equals(tokenInfo.getUuid())) {
                throw new RuntimeException("No access");
            }

            return Response.create_simple_success();
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {

            return Response.create_simple_success(noteService.list(params));
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/my/list")
    @ResponseBody
    public Response myList(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            Map<String,Object> filter = getFilter(params);
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            filter.put("userId", tokenInfo.getUuid());
            params.put("filter", filter);
            return Response.create_simple_success(noteService.list(params));
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    private Map<String, Object> getFilter(Map<String, Object> params) {
        Map<String, Object> filter = (Map<String, Object>) params.get("filter");
        if (filter == null) {
            filter = new HashMap<>();
        }
        return filter;
    }
}
