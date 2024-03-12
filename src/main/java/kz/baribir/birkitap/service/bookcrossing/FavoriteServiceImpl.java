package kz.baribir.birkitap.service.bookcrossing;

import kz.baribir.birkitap.model.bookcrossing.entity.Favorite;
import kz.baribir.birkitap.repository.bookcrossing.FavoriteRepository;
import kz.baribir.birkitap.util.NoSqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Override
    public Favorite create(Favorite favorite) {
        favoriteRepository.save(favorite);
        return favorite;
    }

    @Override
    public void delete(String id) {
        favoriteRepository.deleteById(id, Favorite.class);
    }

    @Override
    public List<Favorite> list(String userId) {

        Map<String, Object> param = new HashMap<>();
        Map<String, Object> filter = new HashMap<>();
        filter.put("creator", userId);
        param.put("filter", filter);
        Map<String, String> param2column = new HashMap<>();
        param2column.put("creator", "creator");
        Query query = NoSqlUtil.queryByParams(param, param2column, false);

        return favoriteRepository.list(query);
    }
}
