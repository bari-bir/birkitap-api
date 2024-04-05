package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.SaveResult;
import kz.baribir.birkitap.components.UploadDownloadUtil;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.common.entity.Book;
import kz.baribir.birkitap.model.common.entity.BookCategory;
import kz.baribir.birkitap.model.common.entity.Review;
import kz.baribir.birkitap.model.common.entity.User;
import kz.baribir.birkitap.service.BookCategoryService;
import kz.baribir.birkitap.service.BookService;
import kz.baribir.birkitap.service.ReviewService;
import kz.baribir.birkitap.service.UserService;
import kz.baribir.birkitap.service.booktracker.BookTrackerService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import kz.baribir.birkitap.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String title = ParamUtil.get_string(params, "title", false);
            String author = ParamUtil.get_string(params, "author", true);
            String imageLink = ParamUtil.get_string(params, "imageLink", true);
            int year = ParamUtil.get_int(params, "year", true);
            String description = ParamUtil.get_string(params, "description", true);
            int page = ParamUtil.get_int(params, "page", true);
            List<String> genres = (List<String>) params.get("genres");

            if (title.isEmpty()) {
                throw new RuntimeException("title should be not empty!");
            }
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setImageLink(imageLink);
            book.setYear(year);
            book.setGenres(genres);
            book.setDescription(description);
            book.setPage(page);

            return Response.create_simple_success(bookService.create(book));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @Autowired
    private BookTrackerService bookTrackerService;

    @RequestMapping("/get")
    @ResponseBody
    public Response get(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(params, "id", false);

            int finishCount = 0;
            int selectedCount = 0;

            var trackerList = bookTrackerService.list(Map.of("filter", Map.of("bookId", id)));
            for (var booktracker: trackerList) {
                if ("selected".equals(booktracker.getStatus())) {
                    selectedCount++;
                }
                if ("finish".equals(booktracker.getStatus())) {
                    finishCount++;
                }
            }
            var reviewList = reviewService.list(Map.of("filter", Map.of("bookId", id)));
            float rating = 0.0f;
            for (var review: reviewList) {
                rating += review.getRating();
            }
            if (reviewList.size() > 0) {
                rating = rating / reviewList.size();
            }

            Book book = bookService.get(id);

            return Response.create_simple_success(
                    Map.of(
                            "book", book,
                            "reviews", reviewList,
                            "customInfo",
                            Map.of(
                                    "finish", finishCount,
                                    "selected", selectedCount,
                                    "review", reviewList.size(),
                                    "rating", rating
                            )));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            return Response.create_simple_success(bookService.list(params));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list/genre")
    @ResponseBody
    public Response listByGenre(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            List<Book> books = bookService.list(params);
            Map<String, List<Book>> result = new HashMap<>();

            for (var book : books) {
                for (var genre : book.getGenres()) {
                    List<Book> curr = result.getOrDefault(genre, new ArrayList<>());
                    curr.add(book);
                    result.put(genre, curr);
                }
            }

            return Response.create_simple_success(result);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list/collections")
    @ResponseBody
    public Response listByCollection(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            List<Book> books = bookService.list(params);
            Map<String, List<Book>> result = new HashMap<>();

            for (var book : books) {
                var coolections = book.getCollections();
                if (coolections == null) {
                    continue;
                }

                for (var collection : book.getCollections()) {
                    if (collection == null || collection.isEmpty()) {
                        continue;
                    }
                    List<Book> curr = result.getOrDefault(collection, new ArrayList<>());
                    curr.add(book);
                    result.put(collection, curr);
                }
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
            SaveResult saveResult = uploadDownloadUtil.save_multipart(file, "public/book", request);
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

    @RequestMapping("/review")
    @ResponseBody
    public Response review(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String bookId = ParamUtil.get_string(params, "bookId", false);
            Map<String, Object> filter = (Map<String, Object>) params.get("filter");
            if (filter == null) {
                filter = new HashMap<>();
            }
            filter.put("bookId", bookId);
            params.put("filter", filter);

            return Response.create_simple_success(reviewService.list(params));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @Autowired
    private UserService userService;

    @RequestMapping("/review/create")
    @ResponseBody
    public Response reviewCreate(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String title = ParamUtil.get_string(params, "title", false);
            String userId = jwtUtils.getTokenInfo(request).getUuid();
            String bookId = ParamUtil.get_string(params, "bookId", false);
            String message = ParamUtil.get_string(params, "message", false);
            int rating = ParamUtil.get_int(params, "rating", false);

            User user = userService.get(userId);
            Review review = new Review();
            review.setTitle(title);
            review.setUserId(userId);
            review.setBookId(bookId);
            review.setMessage(message);
            review.setRating(rating);
            review.setBook(bookService.get(bookId));
            review.setCreatetime(new Date().getTime());
            review.setUserName(user.getFullName());

            return Response.create_simple_success(reviewService.create(review));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @Autowired
    private BookCategoryService bookCategoryService;

    @RequestMapping("/category/create")
    @ResponseBody
    public Response categoryAdd(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String title = ParamUtil.get_string(params, "title", false);
            String icon = ParamUtil.get_string(params, "icon", true);
            String url = ParamUtil.get_string(params, "url", true);
            int visible = ParamUtil.get_int(params, "visible", true);
            int sort = ParamUtil.get_int(params, "sort", true);
            BookCategory bookCategory = new BookCategory();
            bookCategory.setTitle(title);
            bookCategory.setIcon(icon);
            bookCategory.setUrl(url);
            bookCategory.setVisible(visible);
            bookCategory.setSort(sort);

            bookCategoryService.create(bookCategory);

            return Response.create_simple_success(bookCategory);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/category/update")
    @ResponseBody
    public Response categoryUpdate(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(params, "id", false);
            String title = ParamUtil.get_string(params, "title", false);
            String icon = ParamUtil.get_string(params, "icon", true);
            String url = ParamUtil.get_string(params, "url", true);
            int visible = ParamUtil.get_int(params, "visible", true);
            int sort = ParamUtil.get_int(params, "sort", true);
            BookCategory bookCategory = bookCategoryService.get(id);
            bookCategory.setTitle(title);
            bookCategory.setIcon(icon);
            bookCategory.setUrl(url);
            bookCategory.setVisible(visible);
            bookCategory.setSort(sort);

            bookCategoryService.create(bookCategory);

            return Response.create_simple_success(bookCategory);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/category/list")
    @ResponseBody
    public Response categories(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {

            return Response.create_simple_success(bookCategoryService.list(params));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }
}
