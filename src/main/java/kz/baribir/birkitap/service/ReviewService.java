package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.Review;

import java.util.List;
import java.util.Map;

public interface ReviewService {
    Review create(Review review);
    Review get(String id);
    Review update(Review review);
    void delete(String id);
    List<Review> list(Map<String, Object> params);
}
