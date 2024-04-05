package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.Book;
import kz.baribir.birkitap.model.common.entity.Post;
import kz.baribir.birkitap.model.common.entity.Review;
import kz.baribir.birkitap.model.common.entity.User;

import java.util.List;

public interface RecommendService {

    List<Book> recommendBooks(String userId, int start, int length);
    List<Review> recommendReviews(String userId, int start, int length);
    List<Post> recommendPosts(String userId, int start, int length);
    List<User> recommendUsers(String userId, int start, int length);
}
