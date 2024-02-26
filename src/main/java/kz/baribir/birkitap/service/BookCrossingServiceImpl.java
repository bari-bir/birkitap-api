package kz.baribir.birkitap.service;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.CustomQueryParam;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.model.Page;
import kz.baribir.birkitap.model.Response;
import kz.baribir.birkitap.model.entity.BookCrossingRequest;
import kz.baribir.birkitap.model.entity.BookCrossingResponse;
import kz.baribir.birkitap.repository.BookCrossingRequestRepository;
import kz.baribir.birkitap.repository.BookCrossingResponseRepository;
import kz.baribir.birkitap.util.JWTUtils;
import kz.baribir.birkitap.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BookCrossingServiceImpl implements BookCrossingService {

    @Autowired
    private BookCrossingResponseRepository bookCrossingResponseRepository;

    @Autowired
    private BookCrossingRequestRepository bookCrossingRequestRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public Response createRequest(Map<String, Object> param, HttpServletRequest request) {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        String userId = tokenInfo.getUuid();

        String title = ParamUtil.get_string(param, "title", false, false);
        String description = ParamUtil.get_string(param, "description", false, false);
        String year = ParamUtil.get_string(param, "year", false, false);
        String city = ParamUtil.get_string(param, "city", false, false);

        List<String> images = (List<String>) param.getOrDefault("images", new ArrayList<>());

        BookCrossingRequest bookCrossingRequest = new BookCrossingRequest();
        bookCrossingRequest.setTitle(title);
        bookCrossingRequest.setDescription(description);
        bookCrossingRequest.setImages(images);
        bookCrossingRequest.setYear(year);
        bookCrossingRequest.setCity(city);
        bookCrossingRequest.setStatus("created");
        bookCrossingRequest.setUserId(userId);
        bookCrossingRequest.setCreatetime(new Date());
        bookCrossingRequestRepository.save(bookCrossingRequest);

        return Response.create_simple_success(bookCrossingRequest);
    }

    @Override
    public Response createResponse(Map<String, Object> param, HttpServletRequest request) {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        String userId = tokenInfo.getUuid();

        String crossingRequestId = ParamUtil.get_string(param, "requestId", false);
        String description = ParamUtil.get_string(param, "description", false);
        List<String> attachments = (List<String>) param.getOrDefault("attachments", new ArrayList<>());

        BookCrossingRequest crossingRequest = bookCrossingRequestRepository.findById(crossingRequestId);
        if (crossingRequest == null) {
            throw new RuntimeException("Not found crossing request!");
        }

        BookCrossingResponse crossingResponse = new BookCrossingResponse();
        crossingResponse.setUserId(userId);
        crossingResponse.setToUserId(crossingRequest.getUserId());
        crossingResponse.setCrossingRequestId(crossingRequestId);
        crossingResponse.setStatus("created");
        crossingResponse.setAttachments(attachments);
        crossingResponse.setDescription(description);
        crossingResponse.setCreatetime(new Date());

        bookCrossingResponseRepository.save(crossingResponse);

        return Response.create_simple_success(crossingResponse);
    }

    @Override
    public Response getRequest(Map<String, Object> param, HttpServletRequest request) {
        String id = ParamUtil.get_string(param, "id", false);

        BookCrossingRequest crossingRequest = bookCrossingRequestRepository.findById(id);
        if (crossingRequest == null) {
            throw new RuntimeException("Not found");
        }

        return Response.create_simple_success(crossingRequest);
    }

    @Override
    public Response getResponse(Map<String, Object> param, HttpServletRequest request) {
        String id = ParamUtil.get_string(param, "id", false);

        BookCrossingResponse crossingResponse = bookCrossingResponseRepository.findById(id);
        if (crossingResponse == null) {
            throw new RuntimeException("Not found");
        }

        return Response.create_simple_success(crossingResponse);
    }

    @Override
    public Response updateRequest(Map<String, Object> param, HttpServletRequest request) {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        String userId = tokenInfo.getUuid();

        String id = ParamUtil.get_string(param, "id", false, false);
        BookCrossingRequest bookCrossingRequest = bookCrossingRequestRepository.findById(id);
        if (bookCrossingRequest == null) {
            throw new RuntimeException("Not found request!");
        }

        if (bookCrossingRequest.getUserId() != userId) {
            throw new RuntimeException("You have not permissions!");
        }

        String title = ParamUtil.get_string(param, "title", false, false);
        String description = ParamUtil.get_string(param, "description", false, false);
        String year = ParamUtil.get_string(param, "year", false, false);
        String city = ParamUtil.get_string(param, "city", false, false);
        List<String> images = (List<String>) param.getOrDefault("images", new ArrayList<>());

        bookCrossingRequest.setTitle(title);
        bookCrossingRequest.setDescription(description);
        bookCrossingRequest.setImages(images);
        bookCrossingRequest.setYear(year);
        bookCrossingRequest.setCity(city);
        bookCrossingRequest.setUserId(userId);
        bookCrossingRequestRepository.save(bookCrossingRequest);

        return Response.create_simple_success(bookCrossingRequest);
    }

    @Override
    public Response updateResponse(Map<String, Object> param, HttpServletRequest request) {
        TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
        String userId = tokenInfo.getUuid();

        String crossingRequestId = ParamUtil.get_string(param, "requestId", false);
        String description = ParamUtil.get_string(param, "description", false);
        List<String> attachments = (List<String>) param.getOrDefault("attachments", new ArrayList<>());

        BookCrossingRequest crossingRequest = bookCrossingRequestRepository.findById(crossingRequestId);
        if (crossingRequest == null) {
            throw new RuntimeException("Not found crossing request!");
        }

        BookCrossingResponse crossingResponse = new BookCrossingResponse();
        crossingResponse.setUserId(userId);
        crossingResponse.setToUserId(crossingRequest.getUserId());
        crossingResponse.setCrossingRequestId(crossingRequestId);
        crossingResponse.setStatus("created");
        crossingResponse.setAttachments(attachments);
        crossingResponse.setDescription(description);
        crossingResponse.setCreatetime(new Date());

        bookCrossingResponseRepository.save(crossingResponse);

        return null;
    }

    @Override
    public Response deleteRequest(Map<String, Object> param, HttpServletRequest request) {
        return null;
    }

    @Override
    public Response deleteResponse(Map<String, Object> param, HttpServletRequest request) {
        return null;
    }

    @Override
    public Response listRequest(Map<String, Object> param, HttpServletRequest request) {
        CustomQueryParam qp = CustomQueryParam.parse(param);

        Page page = bookCrossingRequestRepository.findPage(qp);

        return Response.create_simple_success(page);
    }

    @Override
    public Response listResponse(Map<String, Object> param, HttpServletRequest request) {
        CustomQueryParam qp = CustomQueryParam.parse(param);

        Page page = bookCrossingResponseRepository.findPage(qp);

        return Response.create_simple_success(page);
    }
}
