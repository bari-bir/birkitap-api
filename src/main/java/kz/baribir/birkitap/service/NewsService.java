package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.News;

import java.util.List;
import java.util.Map;

public interface NewsService {

    News create(News news);
    News get(String id);
    News update(News news);
    void delete(String id);
    List<News> list(Map<String, Object> param);

}
