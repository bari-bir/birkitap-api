package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.News;
import kz.baribir.birkitap.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService{

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public News create(News news) {
        newsRepository.save(news);

        return news;
    }

    @Override
    public News get(String id) {
        News news = newsRepository.findById(id, News.class);

        return news;
    }

    @Override
    public News update(News news) {
        newsRepository.save(news);

        return news;
    }

    @Override
    public void delete(String id) {
        newsRepository.deleteById(id, News.class);
    }

    @Override
    public List<News> list(Map<String, Object> param) {
        return newsRepository.list(param);
    }
}
