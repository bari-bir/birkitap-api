package kz.baribir.birkitap.service.booktracker;

import kz.baribir.birkitap.model.booktracker.entity.BookTracker;

import java.util.List;
import java.util.Map;

public interface BookTrackerService {

    BookTracker create(BookTracker bookTracker);
    BookTracker get(String id);
    BookTracker update(BookTracker bookTracker);
    void delete(String id);
    List<BookTracker> list(Map<String, Object> params);

}
