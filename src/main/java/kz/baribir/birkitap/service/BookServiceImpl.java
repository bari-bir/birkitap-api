package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.Book;
import kz.baribir.birkitap.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book create(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    public Book get(String id) {
        Book book = bookRepository.findById(id, Book.class);
        if (book == null) {
            throw new RuntimeException("book not found");
        }

        return book;
    }

    @Override
    public Book update(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    public void delete(String id) {
        bookRepository.deleteById(id, Book.class);
    }

    @Override
    public List<Book> list(Map<String, Object> params) {
        return bookRepository.list(params);
    }
}
