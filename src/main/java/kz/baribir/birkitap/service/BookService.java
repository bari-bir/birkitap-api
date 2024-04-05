package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.booktracker.entity.BookTracker;
import kz.baribir.birkitap.model.common.entity.Book;

import java.util.List;
import java.util.Map;

public interface BookService {

    Book create(Book book);
    Book get(String id);
    Book update(Book book);
    void delete(String id);
    List<Book> list(Map<String, Object> params);
}
