package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.Post;
import kz.baribir.birkitap.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post create(Post post) {
        postRepository.save(post);
        return post;
    }

    @Override
    public Post get(String id) {
        Post post = postRepository.findById(id, Post.class);
        if (post == null) {
            throw new RuntimeException("post not found");
        }

        return post;
    }

    @Override
    public Post update(Post post) {
        postRepository.save(post);

        return post;
    }

    @Override
    public void delete(String id) {
        postRepository.deleteById(id, Post.class);
    }

    @Override
    public List<Post> list(Map<String, Object> params) {
        return postRepository.list(params);
    }

    @Override
    public List<Post> list(String userId) {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> filter = new HashMap<>();
        filter.put("userId", userId);
        params.put("filter", filter);

        return list(params);
    }

    @Override
    public long count(Map<String, Object> params) {
        return postRepository.count(params);
    }
}
