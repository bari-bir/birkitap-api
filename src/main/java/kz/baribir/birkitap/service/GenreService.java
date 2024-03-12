package kz.baribir.birkitap.service;


import kz.baribir.birkitap.model.entity.Genre;

import java.util.List;

public interface GenreService {
    Genre create(Genre genre);
    List<Genre> list();
}
