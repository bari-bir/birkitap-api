package kz.baribir.birkitap.repository.bookcrossing;

import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.entity.Announcement;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnnouncementRepository extends MongoRepositoryBase<Announcement> {

    public Announcement findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        return findOne(query, Announcement.class);
    }

    public void delete(String id) {
        deleteById(id, Announcement.class);
    }

    public List<Announcement> list(Query query) {
        return find(query, Announcement.class);
    }
}
