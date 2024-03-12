package kz.baribir.birkitap.manager.bookcrossing;

import kz.baribir.birkitap.model.dto.RequestDTO;

import java.util.List;
import java.util.Map;

public interface RequestManager {
    RequestDTO create(RequestDTO requestDTO);
    RequestDTO get(String id);
    RequestDTO update(RequestDTO requestDTO);
    void delete(String id);
    List<RequestDTO> list(Map<String, Object> params);
}
