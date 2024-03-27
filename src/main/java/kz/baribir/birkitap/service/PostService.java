package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.Post;

import java.util.List;
import java.util.Map;

public interface PostService {

    Post create(Post post);
    Post get(String id);
    Post update(Post post);
    void delete(String id);
    List<Post> list(Map<String, Object> params);
    long count(Map<String, Object> params);
}
