package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.entity.User;
import kz.baribir.birkitap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User get(String id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new RuntimeException("user not found!");
        }

        return user;
    }
}
