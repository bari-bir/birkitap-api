package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.model.Response;
import kz.baribir.birkitap.service.AuthService;
import kz.baribir.birkitap.util.PojUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping("/login")
    @ResponseBody
    public Response login(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            return authService.login(params, request);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), PojUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/register")
    @ResponseBody
    public Response register(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            return authService.register(params, request);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), PojUtil.getStackTrace(e));
        }
    }
}
