package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.common.entity.Review;
import kz.baribir.birkitap.service.BookService;
import kz.baribir.birkitap.service.ReviewService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import kz.baribir.birkitap.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookService bookService;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String title = ParamUtil.get_string(params, "title", false);
            String userId = jwtUtils.getTokenInfo(request).getUuid();
            String bookId = ParamUtil.get_string(params, "bookId", false);
            String message = ParamUtil.get_string(params, "message", false);
            int rating = ParamUtil.get_int(params, "rating", false);
            Review review = new Review();
            review.setBook(bookService.get(bookId));
            review.setTitle(title);
            review.setUserId(userId);
            review.setBookId(bookId);
            review.setMessage(message);
            review.setRating(rating);

            return Response.create_simple_success(reviewService.create(review));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/get")
    @ResponseBody
    public Response get(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(params, "id", false);

            Review review = reviewService.get(id);

            return Response.create_simple_success(review);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {

            return Response.create_simple_success(reviewService.list(params));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }


}
