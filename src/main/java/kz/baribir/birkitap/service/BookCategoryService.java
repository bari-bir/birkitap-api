package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.BookCategory;
import kz.baribir.birkitap.model.common.entity.Category;

import java.util.List;
import java.util.Map;

public interface BookCategoryService {

    BookCategory create(BookCategory category);
    BookCategory get(String id);
    BookCategory update(BookCategory category);
    void delete(String id);
    List<BookCategory> list(Map<String, Object> params);
    long count(Map<String, Object> params);
}
