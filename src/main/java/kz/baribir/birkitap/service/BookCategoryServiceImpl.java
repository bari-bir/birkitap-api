package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.BookCategory;
import kz.baribir.birkitap.model.common.entity.Category;
import kz.baribir.birkitap.repository.BookCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Override
    public BookCategory create(BookCategory category) {
        bookCategoryRepository.save(category);
        return category;
    }

    @Override
    public BookCategory get(String id) {
        BookCategory bookCategory = bookCategoryRepository.findById(id, BookCategory.class);
        if (bookCategory == null) {
            throw new RuntimeException("not found!");
        }
        return bookCategory;
    }

    @Override
    public BookCategory update(BookCategory category) {
        bookCategoryRepository.save(category);
        return category;
    }

    @Override
    public void delete(String id) {
        bookCategoryRepository.deleteById(id, BookCategory.class);
    }

    @Override
    public List<BookCategory> list(Map<String, Object> params) {
        return bookCategoryRepository.list(params);
    }

    @Override
    public long count(Map<String, Object> params) {
        return 0;
    }
}
