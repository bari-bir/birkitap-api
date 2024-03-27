package kz.baribir.birkitap.repository;

import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.common.entity.Genre;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreRepository extends MongoRepositoryBase<Genre> {

    public List<Genre> listAll() {
        return findAll(Genre.class);
    }
}
