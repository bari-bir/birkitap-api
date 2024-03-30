package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.common.entity.Book;
import kz.baribir.birkitap.model.common.entity.Post;
import kz.baribir.birkitap.model.common.entity.Review;
import kz.baribir.birkitap.model.common.entity.User;
import kz.baribir.birkitap.service.RecommendService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping("/books")
    @ResponseBody
    public Response books(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            List<Book> books = recommendService.recommendBooks(tokenInfo.getUuid());

            return Response.create_simple_success(books);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/reviews")
    @ResponseBody
    public Response reviews(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            List<Review> reviews = recommendService.recommendReviews(tokenInfo.getUuid());

            return Response.create_simple_success(reviews);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/posts")
    @ResponseBody
    public Response posts(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            List<Post> posts = recommendService.recommendPosts(tokenInfo.getUuid());

            return Response.create_simple_success(posts);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/users")
    @ResponseBody
    public Response users(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            List<User> users = recommendService.recommendUsers(tokenInfo.getUuid());

            return Response.create_simple_success(users);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }
}
