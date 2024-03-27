package kz.baribir.birkitap.service;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.model.common.Response;

import java.util.Map;

public interface AuthService {

    Response login(Map<String, Object> params, HttpServletRequest request);
    Response register(Map<String, Object> params, HttpServletRequest request);
    Response updatePassword(Map<String, Object> params, HttpServletRequest request);
    Response resetPassword(Map<String, Object> params, HttpServletRequest request);
    Response refreshToken(Map<String, Object> params, HttpServletRequest request);

}
