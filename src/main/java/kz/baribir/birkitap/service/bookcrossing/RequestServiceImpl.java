package kz.baribir.birkitap.service.bookcrossing;

import kz.baribir.birkitap.manager.bookcrossing.RequestManager;
import kz.baribir.birkitap.model.common.dto.RequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RequestServiceImpl implements RequestService{

    @Autowired
    private RequestManager requestManager;

    @Override
    public RequestDTO get(String id) {
        RequestDTO requestDTO = requestManager.get(id);
        if (requestDTO == null) {
            throw new RuntimeException("request not found");
        }

        return requestDTO;
    }

    @Override
    public RequestDTO create(RequestDTO requestDTO) {

        return requestManager.create(requestDTO);
    }

    @Override
    public RequestDTO update(RequestDTO requestDTO) {
        requestManager.update(requestDTO);
        return requestDTO;
    }

    @Override
    public void delete(String id) {
        requestManager.delete(id);
    }

    @Override
    public List<RequestDTO> list(Map<String, Object> params) {
        return requestManager.list(params);
    }
}
