package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.User;
import kz.baribir.birkitap.repository.FollowersRepository;
import kz.baribir.birkitap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowersService followersService;

    @Override
    public User get(String id) {
        User user = userRepository.findById(id, User.class);
        if (user == null) {
            throw new RuntimeException("user not found!");
        }

        return user;
    }

    @Override
    public User update(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> findByIds(List<String> ids) {
        return userRepository.findByIds(ids);
    }

    @Override
    public List<User> myFollowers(String id) {
        List<String> followersIds = followersService.findByToUser(id);
        List<User> followers = findByIds(followersIds);

        return followers;
    }

    @Override
    public List<User> myFollowings(String id) {
        List<String> followersIds = followersService.findByFromUser(id);
        List<User> followers = findByIds(followersIds);

        return followers;
    }
}
