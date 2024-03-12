package kz.baribir.birkitap.service.bookcrossing;

import kz.baribir.birkitap.model.dto.RequestDTO;
import kz.baribir.birkitap.model.entity.Request;

import java.util.List;
import java.util.Map;

public interface RequestService {

    RequestDTO get(String id);
    RequestDTO create(RequestDTO requestDTO);
    RequestDTO update(RequestDTO requestDTO);
    void delete(String id);
    List<RequestDTO> list(Map<String, Object> params);
}
