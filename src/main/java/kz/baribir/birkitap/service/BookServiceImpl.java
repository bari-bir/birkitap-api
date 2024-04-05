package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.Book;
import kz.baribir.birkitap.model.common.entity.Review;
import kz.baribir.birkitap.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewService reviewService;

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
        List<Book> list = bookRepository.list(params);

        for (var book : list) {
            float rating = 0.0f;
            List<Review> reviews = reviewService.findByBookId(book.getId());
            for (var review : reviews) {
                rating += review.getRating();
            }


            if (!reviews.isEmpty()) {
                rating = rating / reviews.size();
            }
            book.setRating(rating);
        }

        return list;
    }
}
