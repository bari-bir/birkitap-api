package kz.baribir.birkitap.model.common;

import kz.baribir.birkitap.model.booktracker.entity.BookTracker;
import kz.baribir.birkitap.model.common.entity.Book;
import kz.baribir.birkitap.model.common.entity.Post;
import kz.baribir.birkitap.model.common.entity.Review;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Profile {

    private int readBooksCount;
    private int reviewsCount;
    private int followersCount;
    private int followingCount;
    private List<Review> reviews;
    private Map<String, Object> books;
    private List<Post> posts;
}
