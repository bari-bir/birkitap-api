package kz.baribir.birkitap.service.bookcrossing;

import kz.baribir.birkitap.model.dto.AnnouncementDTO;
import kz.baribir.birkitap.model.entity.Announcement;

import java.util.List;
import java.util.Map;

public interface AnnouncementService {

    AnnouncementDTO get(String id);
    AnnouncementDTO create(AnnouncementDTO announcementDTO);
    AnnouncementDTO update(AnnouncementDTO announcementDTO);
    void delete(String id);
    List<AnnouncementDTO> list(Map<String, Object> params);
}
