package kz.baribir.birkitap.repository;

import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.common.entity.Book;
import kz.baribir.birkitap.model.common.entity.Followers;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowersRepository extends MongoRepositoryBase<Followers> {

    public List<Followers> findByToUser(String toUserId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("toUserId").is(toUserId));

        return find(query, Followers.class);
    }

    public List<Followers> findByFromUser(String fromUserId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("fromUserId").is(fromUserId));

        return find(query, Followers.class);
    }

    public Followers findByTwoFollows(String toUserId, String fromUserId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("toUserId").is(toUserId));
        query.addCriteria(Criteria.where("fromUserId").is(fromUserId));

        return findOne(query, Followers.class);
    }
}
