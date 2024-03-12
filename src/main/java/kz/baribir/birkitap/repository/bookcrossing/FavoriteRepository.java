package kz.baribir.birkitap.repository.bookcrossing;

import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.bookcrossing.entity.Favorite;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoriteRepository extends MongoRepositoryBase<Favorite> {

    public List<Favorite> list(Query query) {
        return find(query, Favorite.class);
    }
}
