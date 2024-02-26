package kz.baribir.birkitap.repository;

import kz.baribir.birkitap.model.entity.User;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractMongoRepository<User>{

    public User findOneByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        return findOne(query);
    }

    public User findOneByPhone(String phone) {
        Query query = new Query();
        query.addCriteria(Criteria.where("phone").is(phone));

        return findOne(query);
    }

    public User findOneByRefreshToken(String refreshToken) {
        Query query = new Query();
        query.addCriteria(Criteria.where("refreshToken").is(refreshToken));

        return findOne(query);
    }

}

