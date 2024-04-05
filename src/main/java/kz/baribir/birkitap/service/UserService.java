package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.User;

import java.util.List;

public interface UserService {

    User get(String id);

    User update(User user);

    List<User> findByIds(List<String> ids);

    List<User> myFollowers(String id);
    List<User> myFollowings(String id);
}
