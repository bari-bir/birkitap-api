package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.Followers;
import kz.baribir.birkitap.repository.FollowersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowersServiceImpl implements FollowersService {

    @Autowired
    private FollowersRepository followersRepository;

    @Override
    public Followers create(Followers followers) {
        followersRepository.save(followers);
        return followers;
    }

    @Override
    public List<String> findByToUser(String toUserId) {
        List<String> fromUserIds =  new ArrayList<>();
        var followers = followersRepository.findByToUser(toUserId);
        for (var follows : followers) {
            fromUserIds.add(follows.getFromUserId());
        }

        return fromUserIds;
    }

    @Override
    public List<String> findByFromUser(String fromUserId) {
        List<String> toUserIds = new ArrayList<>();
        var followers = followersRepository.findByFromUser(fromUserId);
        for (var follows : followers) {
            toUserIds.add(follows.getToUserId());
        }

        return toUserIds;
    }

    @Override
    public void delete(String id) {
        followersRepository.deleteById(id, Followers.class);
    }

    @Override
    public Followers get(String toUserId, String fromUserId) {
        Followers followers = followersRepository.findByTwoFollows(toUserId, fromUserId);

        return followers;
    }

    @Override
    public Followers get(String id) {
        return followersRepository.findById(id, Followers.class);
    }
}
