package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.model.Response;
import kz.baribir.birkitap.service.BookCrossingService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.PojUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/bookcrossing/request")
public class BookCrossingRequestController {

    @Autowired
    private BookCrossingService bookCrossingService;

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            return bookCrossingService.createRequest(params, request);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            return bookCrossingService.listRequest(params, request);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

}
