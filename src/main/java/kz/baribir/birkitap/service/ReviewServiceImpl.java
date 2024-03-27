package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.Review;
import kz.baribir.birkitap.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review create(Review review) {
        reviewRepository.save(review);
        return review;
    }

    @Override
    public Review get(String id) {
        Review review = reviewRepository.findById(id, Review.class);
        if (review == null) {
            throw new RuntimeException("review not found");
        }
        return review;
    }

    @Override
    public Review update(Review review) {
        reviewRepository.save(review);
        return review;
    }

    @Override
    public void delete(String id) {
        reviewRepository.deleteById(id, Review.class);
    }

    @Override
    public List<Review> list(Map<String, Object> params) {
        return reviewRepository.list(params);
    }
}
