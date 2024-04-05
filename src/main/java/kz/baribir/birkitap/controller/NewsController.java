package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.SaveResult;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.components.UploadDownloadUtil;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.common.entity.News;
import kz.baribir.birkitap.service.NewsService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UploadDownloadUtil uploadDownloadUtil;

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            String title = ParamUtil.get_string(param, "title", false);
            String content = ParamUtil.get_string(param, "content", false);
            String imageLink = ParamUtil.get_string(param, "imageLink", false);
            String verticalImageLink = ParamUtil.get_string(param, "verticalImageLink", true);

            News news = new News();
            news.setContent(content);
            news.setTitle(title);
            news.setCreatetime(System.currentTimeMillis());
            news.setImageLink(imageLink);
            news.setVerticalImageLink(verticalImageLink);
            newsService.create(news);

            return Response.create_simple_success(news);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/get")
    @ResponseBody
    public Response get(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(param, "id", false);

            News news = newsService.get(id);
            if (news == null) {
                throw new RuntimeException("not found");
            }

            return Response.create_simple_success(news);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Response update(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(param, "id", false);
            String title = ParamUtil.get_string(param, "title", false);
            String content = ParamUtil.get_string(param, "content", false);
            String imageLink = ParamUtil.get_string(param, "imageLink", false);
            String verticalImageLink = ParamUtil.get_string(param, "verticalImageLink", true);

            News news = newsService.get(id);
            news.setContent(content);
            news.setTitle(title);
            news.setCreatetime(System.currentTimeMillis());
            news.setImageLink(imageLink);
            news.setVerticalImageLink(verticalImageLink);

            newsService.update(news);

            return Response.create_simple_success(news);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }


    @RequestMapping("/delete")
    @ResponseBody
    public Response delete(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(param, "id", false);

            newsService.delete(id);

            return Response.create_simple_success();
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            return Response.create_simple_success(newsService.list(param));
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @PostMapping("/upload")
    @ResponseBody
    public Response upload_file(@RequestParam("file") MultipartFile file, HttpServletRequest request)
    {
        try {
            SaveResult saveResult = uploadDownloadUtil.save_multipart(file, "public/news", request);
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
}
