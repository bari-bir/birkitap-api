package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.entity.Genre;
import kz.baribir.birkitap.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public Genre create(Genre genre) {
        genreRepository.save(genre);
        return genre;
    }

    @Override
    public List<Genre> list() {
        return genreRepository.listAll();
    }
}
