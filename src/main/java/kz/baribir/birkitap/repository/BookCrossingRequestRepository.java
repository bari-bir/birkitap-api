package kz.baribir.birkitap.repository;

import kz.baribir.birkitap.bean.CustomQueryParam;
import kz.baribir.birkitap.bean.MongoRepositoryBase;
import kz.baribir.birkitap.model.entity.BookCrossingRequest;
import kz.baribir.birkitap.model.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BookCrossingRequestRepository extends MongoRepositoryBase<BookCrossingRequest> {
    public Page findPage(CustomQueryParam queryParam){
        Query query = new Query();
        return findPageByQueryParam(query, BookCrossingRequest.class, queryParam);
    }

    public BookCrossingRequest findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        return findOne(query, BookCrossingRequest.class);
    }

}
