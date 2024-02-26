package kz.baribir.birkitap.service;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.model.Response;

import java.util.Map;

public interface BookCrossingService {

    Response createRequest(Map<String, Object> param, HttpServletRequest request);
    Response createResponse(Map<String, Object> param, HttpServletRequest request);

    Response getRequest(Map<String, Object> param, HttpServletRequest request);
    Response getResponse(Map<String, Object> param, HttpServletRequest request);

    Response updateRequest(Map<String, Object> param, HttpServletRequest request);
    Response updateResponse(Map<String, Object> param, HttpServletRequest request);

    Response deleteRequest(Map<String, Object> param, HttpServletRequest request);
    Response deleteResponse(Map<String, Object> param, HttpServletRequest request);

    Response listRequest(Map<String, Object> param, HttpServletRequest request);
    Response listResponse(Map<String, Object> param, HttpServletRequest request);
}
