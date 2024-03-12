package kz.baribir.birkitap.repository.bookcrossing;

import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.entity.Announcement;
import kz.baribir.birkitap.model.entity.Request;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RequestRepository  extends MongoRepositoryBase<Request> {

    public Request findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        return findOne(query, Request.class);
    }

    public void delete(String id) {
        deleteById(id, Request.class);
    }

    public List<Request> list(Query query) {
        return find(query, Request.class);
    }
}
