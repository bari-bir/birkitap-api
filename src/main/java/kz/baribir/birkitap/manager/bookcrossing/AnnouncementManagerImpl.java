package kz.baribir.birkitap.manager.bookcrossing;

import kz.baribir.birkitap.model.common.dto.AnnouncementDTO;
import kz.baribir.birkitap.model.common.entity.Announcement;
import kz.baribir.birkitap.repository.bookcrossing.AnnouncementRepository;
import kz.baribir.birkitap.util.ParamUtil;
import kz.baribir.birkitap.util.TimeUtil;
import kz.baribir.birkitap.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AnnouncementManagerImpl implements AnnouncementManager {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Override
    public AnnouncementDTO get(String id) {
        Announcement announcement = announcementRepository.findById(id);
        AnnouncementDTO announcementDTO = Util.copyProperties(announcement, AnnouncementDTO.class);

        return announcementDTO;
    }

    @Override
    public AnnouncementDTO create(AnnouncementDTO announcementDTO) {
        Announcement announcement = Util.copyProperties(announcementDTO, Announcement.class);
        announcementRepository.save(announcement);

        return Util.copyProperties(announcement, AnnouncementDTO.class);
    }

    @Override
    public AnnouncementDTO update(AnnouncementDTO announcementDTO) {
        Announcement announcement = Util.copyProperties(announcementDTO, Announcement.class);
        announcementRepository.save(announcement);

        return Util.copyProperties(announcement, AnnouncementDTO.class);
    }

    @Override
    public void delete(String id) {
        announcementRepository.delete(id);
    }

    @Override
    public List<AnnouncementDTO> list(Map<String, Object> params) {
        Map<String, String> param2column = new HashMap<>();
        param2column.put("title", "title");
        param2column.put("category", "category");
        param2column.put("location", "location");
        param2column.put("year", "year");
        param2column.put("creator", "creator");
        param2column.put("createtime", "createtime");
        param2column.put("updatetime", "updatetime");
        param2column.put("description", "description");

        Query query = queryByParams(params, param2column, true);

        return Util.copyPropertiesList(announcementRepository.list(query), AnnouncementDTO.class);
    }

    private Query queryByParams(Map<String, Object> params, Map<String, String> param2column, boolean withLength) {
        Query query = new Query();
        Map<String, Object> filter = (Map<String, Object>) params.get("filter");
        if (filter != null) {
            for (var entrySet : filter.entrySet()) {
                String columnName = param2column.get(entrySet.getKey());
                if (columnName == null) {
                    continue;
                }
                Object value = entrySet.getValue();
                if (columnName != null && !columnName.isEmpty() && !value.equals("")) {
                    query.addCriteria(Criteria.where(columnName).is(value));
                }
            }
        }

        List<Map<String, Object>> sort = (List<Map<String, Object>>) params.getOrDefault("sort", new ArrayList<>());
        for (var map : sort) {
            String colName = param2column.get(map.get("key"));
            int isAsc = (int) map.get("isAsc");
            Sort.Direction direction = (isAsc == 0) ? Sort.Direction.DESC : Sort.Direction.ASC;
            if (colName != null) {
                query.with(Sort.by(direction, colName));
            }
        }

        List<Map<String, Object>> times = (List<Map<String, Object>>) params.get("time");
        if (times != null) {
            for (var time : times) {
                String time_name = param2column.get((String) time.get("key"));
                long min = (Integer) time.get("min") ;
                long max = (Integer) time.get("max") ;
                Criteria criteria = null;
                if (time_name != null && min != 0) {
                    criteria = Criteria.where(time_name).gte(TimeUtil.timestamp_sec_to_date(min));
                }
                if (time_name != null && max != 0) {
                    if (criteria == null)
                        criteria = Criteria.where(time_name).lte(TimeUtil.timestamp_sec_to_date(max));
                    else
                        criteria.lte(TimeUtil.timestamp_sec_to_date(max));
                }
                if (criteria != null) query.addCriteria(criteria);
            }
        }

        if (withLength) {
            long start = ParamUtil.get_long(params, "start", true, 0L);
            int limit = ParamUtil.get_int(params, "length", true);
            if (limit == 0) {
                limit = 20;
            }
            query.skip(start).limit(limit);
        }

        return query;
    }
}
