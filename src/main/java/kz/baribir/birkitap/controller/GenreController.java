package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.common.entity.Genre;
import kz.baribir.birkitap.service.GenreService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/genre")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            String title = ParamUtil.get_string(param, "title", false);
            Genre genre = new Genre();
            genre.setTitle(title);

            return Response.create_simple_success(genreService.create(genre));
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {

            return Response.create_simple_success(genreService.list());
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

}

