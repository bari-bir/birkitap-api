package kz.baribir.birkitap.repository;

import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.common.entity.Post;
import kz.baribir.birkitap.model.common.entity.User;
import kz.baribir.birkitap.util.NoSqlUtil;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository  extends MongoRepositoryBase<User> {

    public User findOneByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        return findOne(query, User.class);
    }

    public User findOneByPhone(String phone) {
        Query query = new Query();
        query.addCriteria(Criteria.where("phone").is(phone));

        return findOne(query, User.class);
    }

    public User findOneByRefreshToken(String refreshToken) {
        Query query = new Query();
        query.addCriteria(Criteria.where("refreshToken").is(refreshToken));

        return findOne(query, User.class);
    }

    public List<User> findByIds(List<String> ids) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(ids));

        return find(query, User.class);
    }

    public List<User> list(Map<String, Object> param) {
        Map<String, String> param2column = new HashMap<>();
        param2column.put("id", "id");
        param2column.put("createtime", "createtime");
        param2column.put("updatetime", "updatetime");
        Query query = NoSqlUtil.queryByParams(param, param2column, true);

        return find(query, User.class);
    }
}

