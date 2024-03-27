package kz.baribir.birkitap.service;

import kz.baribir.birkitap.model.common.entity.Notification;
import kz.baribir.birkitap.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification create(Notification notification) {
        notificationRepository.save(notification);
        return notification;
    }

    @Override
    public Notification get(String id) {
        Notification notification = notificationRepository.findById(id, Notification.class);
        if (notification == null) {
            throw new RuntimeException("not found");
        }

        return notification;
    }

    @Override
    public Notification update(Notification notification) {
        notificationRepository.save(notification);
        return notification;
    }

    @Override
    public void delete(String id) {
        notificationRepository.deleteById(id, Notification.class);
    }

    @Override
    public List<Notification> list(Map<String, Object> params) {
        return notificationRepository.list(params);
    }

    @Override
    public long count(Map<String, Object> params) {
        return notificationRepository.count(params);
    }
}
