package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.Followers;

import java.util.List;

public interface FollowersService {

    Followers create(Followers followers);
    List<String> findByToUser(String toUserId);
    List<String> findByFromUser(String fromUserId);
    void delete(String id);
    Followers get(String toUserId, String fromUserId);
    Followers get(String id);
}
