package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.User;

public interface UserService {

    User get(String id);

    User update(User user);
}
