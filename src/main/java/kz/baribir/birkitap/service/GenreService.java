package kz.baribir.birkitap.service;


import kz.baribir.birkitap.model.common.entity.Genre;

import java.util.List;

public interface GenreService {
    Genre create(Genre genre);
    Genre get(String id);
    List<Genre> list();
}
