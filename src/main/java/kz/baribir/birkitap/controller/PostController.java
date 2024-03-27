package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.SaveResult;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.components.UploadDownloadUtil;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.common.entity.Post;
import kz.baribir.birkitap.service.PostService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import kz.baribir.birkitap.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PostService postService;

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            String title = ParamUtil.get_string(param, "title", false);
            String content = ParamUtil.get_string(param, "content", false);
            List<String> attachments = (List<String>) param.getOrDefault("attachments", new ArrayList<>());

            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            Post post = new Post();
            post.setAttachments(attachments);
            post.setTitle(title);
            post.setContent(content);
            post.setCreatetime(System.currentTimeMillis());
            post.setUpdatetime(System.currentTimeMillis());
            post.setUserId(tokenInfo.getUuid());
            postService.create(post);

            return Response.create_simple_success(post);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/get")
    @ResponseBody
    public Response get(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(param, "id", false);

            Post post = postService.get(id);

            return Response.create_simple_success(post);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Response update(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(param, "id", false);

            Post post = postService.get(id);

            String title = ParamUtil.get_string(param, "title", false);
            String content = ParamUtil.get_string(param, "content", false);
            List<String> attachments = (List<String>) param.getOrDefault("attachments", new ArrayList<>());

            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            if (!post.getUserId().equals(tokenInfo.getUuid())) {
                throw new RuntimeException("not allowed update");
            }

            post.setAttachments(attachments);
            post.setTitle(title);
            post.setContent(content);
            post.setUpdatetime(System.currentTimeMillis());

            postService.update(post);
            return Response.create_simple_success(post);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Response delete(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(param, "id", false);

            Post post = postService.get(id);

            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            if (!post.getUserId().equals(tokenInfo.getUuid())) {
                throw new RuntimeException("not allowed delete");
            }

            postService.delete(id);

            return Response.create_simple_success();
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {


            return Response.create_simple_success(postService.list(param));
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }


    @RequestMapping("/my/list")
    @ResponseBody
    public Response myList(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            Map<String,Object> filter = (Map<String, Object>) param.get("filter");
            if (filter == null) {
                filter = new HashMap<>();
            }

            filter.put("userId", tokenInfo.getUuid());
            param.put("filter", filter);

            return Response.create_simple_success(postService.list(param));
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
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
            SaveResult saveResult = uploadDownloadUtil.save_multipart(file, "public/" + tokenInfo.getUuid(), request);
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
