package kz.baribir.birkitap.service.booktracker;

import kz.baribir.birkitap.model.booktracker.entity.BookTracker;
import kz.baribir.birkitap.repository.booktracker.BookTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookTrackerServiceImpl implements BookTrackerService {

    @Autowired
    private BookTrackerRepository bookTrackerRepository;

    @Override
    public BookTracker create(BookTracker bookTracker) {
        bookTrackerRepository.save(bookTracker);
        return bookTracker;
    }

    @Override
    public BookTracker get(String id) {
        BookTracker bookTracker = bookTrackerRepository.findById(id);
        if (bookTracker == null) {
            throw new RuntimeException("book tracker not found");

        }
        return bookTracker;
    }

    @Override
    public BookTracker update(BookTracker bookTracker) {
        bookTrackerRepository.save(bookTracker);
        return bookTracker;
    }

    @Override
    public void delete(String id) {
        bookTrackerRepository.deleteById(id, BookTracker.class);
    }

    @Override
    public List<BookTracker> list(Map<String, Object> params) {

        return bookTrackerRepository.list(params);
    }

    @Override
    public List<BookTracker> list(String userId) {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> filter = new HashMap<>();
        filter.put("userId", userId);
        params.put("filter", filter);

        return list(params);
    }
}
