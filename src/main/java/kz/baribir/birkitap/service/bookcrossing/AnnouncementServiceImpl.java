package kz.baribir.birkitap.service.bookcrossing;

import kz.baribir.birkitap.manager.bookcrossing.AnnouncementManager;
import kz.baribir.birkitap.model.dto.AnnouncementDTO;
import kz.baribir.birkitap.model.entity.Announcement;
import kz.baribir.birkitap.repository.bookcrossing.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementManager announcementManager;

    @Override
    public AnnouncementDTO get(String id) {
        AnnouncementDTO announcement = announcementManager.get(id);
        if (announcement == null) {
            throw new RuntimeException("announcement not found!");
        }

        return announcement;
    }

    @Override
    public AnnouncementDTO create(AnnouncementDTO announcementDTO) {
        return announcementManager.create(announcementDTO);
    }

    @Override
    public AnnouncementDTO update(AnnouncementDTO announcementDTO) {
        announcementManager.update(announcementDTO);
        return announcementDTO;
    }

    @Override
    public void delete(String id) {

        announcementManager.delete(id);
    }

    @Override
    public List<AnnouncementDTO> list(Map<String, Object> params) {
        return announcementManager.list(params);
    }
}
