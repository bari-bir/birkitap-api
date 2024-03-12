package kz.baribir.birkitap.service.bookcrossing;

import kz.baribir.birkitap.model.bookcrossing.entity.Favorite;

import java.util.List;

public interface FavoriteService {

    Favorite create(Favorite favorite);
    void delete(String id);
    List<Favorite> list(String userId);
}
