package kz.baribir.birkitap.service;


import kz.baribir.birkitap.model.common.entity.Notification;

import java.util.List;
import java.util.Map;

public interface NotificationService {

    Notification create(Notification notification);
    Notification get(String id);
    Notification update(Notification notification);
    void delete(String id);
    List<Notification> list(Map<String, Object> params);
    long count(Map<String, Object> params);
}
