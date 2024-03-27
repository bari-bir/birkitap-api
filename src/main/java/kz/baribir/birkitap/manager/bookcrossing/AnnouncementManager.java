package kz.baribir.birkitap.manager.bookcrossing;

import kz.baribir.birkitap.model.common.dto.AnnouncementDTO;

import java.util.List;
import java.util.Map;

public interface AnnouncementManager {

    AnnouncementDTO get(String id);
    AnnouncementDTO create(AnnouncementDTO announcementDTO);
    AnnouncementDTO update(AnnouncementDTO announcementDTO);
    void delete(String id);
    List<AnnouncementDTO> list(Map<String, Object> params);
}
