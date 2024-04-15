package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.Book;
import kz.baribir.birkitap.model.common.entity.Post;
import kz.baribir.birkitap.model.common.entity.Review;
import kz.baribir.birkitap.model.common.entity.User;
import kz.baribir.birkitap.repository.BookRepository;
import kz.baribir.birkitap.repository.PostRepository;
import kz.baribir.birkitap.repository.ReviewRepository;
import kz.baribir.birkitap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Book> recommendBooks(String userId, int start, int length) {

        List<Book> books = bookRepository.list(getParams(start, length));

        return books;
    }

    @Override
    public List<Review> recommendReviews(String userId, int start, int length) {
        List<Review> reviews = reviewRepository.list(getParams(start, length));
        List<Review> result = new ArrayList<>();
        for (int i = 0; i < reviews.size(); i++) {
            if (!reviews.get(i).getUserId().equals(userId)) {
                result.add(reviews.get(i));
            }
        }

        return result;
    }

    @Override
    public List<Post> recommendPosts(String userId, int start, int length) {
        List<Post> posts = postRepository.list(getParams(start, length));
        List<Post> result = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            if (!posts.get(i).getUserId().equals(userId)) {
                result.add(posts.get(i));
            }
        }

        return result;
    }

    @Autowired
    private FollowersService followersService;

    @Override
    public List<User> recommendUsers(String userId, int start, int length) {
        List<User> users = userRepository.list(getParams(start, length));

        var followers = followersService.findByFromUser(userId);
        Set<String> followersSet = new HashSet<>(followers);

        List<User> result = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).getId().equals(userId)) {
                if (followersSet.contains(users.get(i).getId())) {
                    users.get(i).setFollowed(true);
                }
                result.add(users.get(i));
            }
        }

        return result;
    }

    private Map<String, Object> getParams(int start, int length) {
        return Map.of("start", start, "length", length);
    }
}
