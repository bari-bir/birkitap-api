package kz.baribir.birkitap.controller.booktracker;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.SaveResult;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.components.UploadDownloadUtil;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.booktracker.entity.BookTracker;
import kz.baribir.birkitap.model.common.entity.Book;
import kz.baribir.birkitap.service.BookService;
import kz.baribir.birkitap.service.booktracker.BookTrackerService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import kz.baribir.birkitap.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/booktracker")
public class BookTrackerController {

    @Autowired
    private BookTrackerService bookTrackerService;

    @Autowired
    private BookService bookService;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String title = ParamUtil.get_string(params, "title", false);
            String bookId = ParamUtil.get_string(params, "bookId", true);
            String image = ParamUtil.get_string(params, "iamge", true);
            int page = ParamUtil.get_int(params, "page", true);
            Book book;
            try  {
                book = bookService.get(bookId);
            } catch (Exception e) {
                book = new Book();
                book.setTitle(title);
                book.setImageLink(image);
                book.setPage(page);
            }

            BookTracker bookTracker = new BookTracker();
            bookTracker.setTitle(title);
            bookTracker.setCreatetime(new Date());
            bookTracker.setUpdatetime(new Date());
            bookTracker.setStatus("selected");
            bookTracker.setImage(image);
            bookTracker.setBook(book);
            bookTracker.setPage(page);
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            bookTracker.setUserId(tokenInfo.getUuid());
            bookService.create(book);

            return Response.create_simple_success(bookTrackerService.create(bookTracker));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/get")
    @ResponseBody
    public Response get(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(params, "id", false);

            BookTracker bookTracker = bookTrackerService.get(id);


            return Response.create_simple_success((bookTracker));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Response update(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(params, "id", false);
            String bookId = ParamUtil.get_string(params, "bookId", true);
            String image = ParamUtil.get_string(params, "iamge", true);

            BookTracker bookTracker = bookTrackerService.get(id);
            //TODO chcek own booktracker
            String title = ParamUtil.get_string(params, "title", false);
            String status = ParamUtil.get_string(params, "status", false);
            bookTracker.setTitle(title);
            bookTracker.setStatus(status);
            bookTracker.setUpdatetime(new Date());
            bookTracker.setImage(image);

            return Response.create_simple_success((bookTracker));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Response delete(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(params, "id", false);

            BookTracker bookTracker = bookTrackerService.get(id);
            //TODO check own booktracker
            bookTrackerService.delete(id);

            return Response.create_simple_success();
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            Map<String, Object> filter = (Map<String, Object>) params.get("filter");
            if (filter == null) {
                filter = new HashMap<>();
            }
            filter.put("userId", tokenInfo.getUuid());
            params.put("filter", filter);

            return Response.create_simple_success(bookTrackerService.list(params));
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
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            SaveResult saveResult = uploadDownloadUtil.save_multipart(file, "public/booktracker", request);
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

    @RequestMapping("/focus")
    @ResponseBody
    public Response focus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(params, "bookTrackerId", false);
            int time = ParamUtil.get_int(params, "time", false);
            int page = ParamUtil.get_int(params, "page", false);

            BookTracker bookTracker = bookTrackerService.get(id);

            bookTracker.setProgressPage(bookTracker.getProgressPage() + page);
            bookTracker.setTime(bookTracker.getTime() + time);

            bookTrackerService.update(bookTracker);

            return Response.create_simple_success(bookTracker);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }
}
