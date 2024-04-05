package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.model.common.Profile;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.common.UserVO;
import kz.baribir.birkitap.model.common.entity.Book;
import kz.baribir.birkitap.model.common.entity.Followers;
import kz.baribir.birkitap.model.common.entity.Genre;
import kz.baribir.birkitap.model.common.entity.User;
import kz.baribir.birkitap.service.*;
import kz.baribir.birkitap.service.booktracker.BookTrackerService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import kz.baribir.birkitap.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PostService postService;

    @Autowired
    private BookTrackerService bookTrackerService;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping("/profile")
    @ResponseBody
    public Response profile(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            var followers = userService.myFollowers(tokenInfo.getUuid());
            var followings = userService.myFollowings(tokenInfo.getUuid());
            var reviews = reviewService.findByUserId(tokenInfo.getUuid());
            var posts = postService.list(tokenInfo.getUuid());
            var books = bookTrackerService.list(tokenInfo.getUuid());

            int readBookCount = 0;
            for (var bookTracker : books) {
                if ("finish".equals(bookTracker.getStatus())) {
                    readBookCount++;
                }
            }

            Map<String, Object> bookByStatus = new HashMap<>();
            for (var book : books) {
                if (book.getBook() != null) {
                    var list = (List<Book>) bookByStatus.getOrDefault(book.getStatus(), new ArrayList<>());
                    list.add(book.getBook());
                    bookByStatus.put(book.getStatus(), list);
                }
            }


            Profile profile = new Profile();
            profile.setPosts(posts);
            profile.setReviews(reviews);
            profile.setFollowersCount(followers.size());
            profile.setFollowingCount(followings.size());
            profile.setReviewsCount(reviews.size());
            profile.setReadBooksCount(readBookCount);
            profile.setBooks(bookByStatus);

            return Response.create_simple_success(profile);
        } catch (Exception e) {

            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/profile/edit")
    @ResponseBody
    public Response profileEdit(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            User user = userService.get(tokenInfo.getUuid());
            String fullName = ParamUtil.get_string(params, "fullName", false);
            long birth = ParamUtil.get_long(params, "birth", false, 0L);
            String gender = ParamUtil.get_string(params, "gender", false);
            String avatar = ParamUtil.get_string(params, "avatar", true);

            if (fullName.isEmpty()) {
                throw new RuntimeException("fullName should be not empty");
            }

            if (gender.isEmpty()) {
                throw new RuntimeException("gender should be not empty");
            }

            if (!"male".equals(gender) && !"female".equals(gender)) {
                throw new RuntimeException("gender should male or female");
            }


            user.setFullName(fullName);
            user.setBirth(new Date(birth));
            user.setGender(gender);
            if (avatar != null || !avatar.isEmpty()) {
                user.setAvatar(avatar);
            }

            userService.update(user);

            return Response.create_simple_success();
        } catch (Exception e) {

            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/info")
    @ResponseBody
    public Response info(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            User user = userService.get(tokenInfo.getUuid());

            return Response.create_simple_success(new UserVO().mapper(user));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/genre/add")
    @ResponseBody
    public Response addGenre(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            List<Genre> genres = (List<Genre>) params.getOrDefault("genres", new ArrayList<>());

            User user = userService.get(tokenInfo.getUuid());
            user.setGenres(genres);
            userService.update(user);
            return Response.create_simple_success();
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @Autowired
    private FollowersService followersService;

    @RequestMapping("/follow")
    @ResponseBody
    public Response follow(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String toUserId = ParamUtil.get_string(params, "toUserId", false);

            User user = userService.get(toUserId);
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            Followers followers = new Followers();
            followers.setToUserId(toUserId);
            followers.setFromUserId(tokenInfo.getUuid());
            followersService.create(followers);

            return Response.create_simple_success();
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/unfollow")
    @ResponseBody
    public Response unfollow(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String toUserId = ParamUtil.get_string(params, "toUserId", false);

            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            Followers followers = followersService.get(toUserId, tokenInfo.getUuid());

            followersService.delete(followers.getId());

            return Response.create_simple_success();
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/my/followers")
    @ResponseBody
    public Response myFollowers(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            List<String> followersIds = followersService.findByToUser(tokenInfo.getUuid());
            List<User> followers = userService.findByIds(followersIds);

            return Response.create_simple_success(followers);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/my/following")
    @ResponseBody
    public Response myFollowing(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);

            List<String> followersIds = followersService.findByFromUser(tokenInfo.getUuid());
            List<User> followers = userService.findByIds(followersIds);

            return Response.create_simple_success(followers);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }


}
