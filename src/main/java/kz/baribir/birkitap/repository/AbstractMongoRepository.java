package kz.baribir.birkitap.repository;

import jakarta.annotation.Resource;
import kz.baribir.birkitap.model.common.Page;
import kz.baribir.birkitap.util.ContainerUtil;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public abstract class AbstractMongoRepository<E> {
    @Resource
    protected MongoTemplate mongoTemplate;
    private final Class<E> instance;

    protected AbstractMongoRepository(){
        Type type = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) type;
        Object obj = pt.getActualTypeArguments()[0];
        instance = (Class<E>) obj;
    }
    protected String getCollectionName(){
        return instance.getSimpleName();
    }

    public E save(E e)
    {
       return mongoTemplate.save(e,getCollectionName());
    }
    public E findById(String id)
    {
        return mongoTemplate.findById(id, instance, getCollectionName());
    }
    public List<E> findList(Query query){
        return mongoTemplate.find(query, instance, getCollectionName());
    }
    public E findOne(Query query){
        return mongoTemplate.findOne(query, instance, getCollectionName());
    }

    public List<E> findAll()
    {
        return mongoTemplate.findAll(instance, getCollectionName());
    }

    public void deleteById(String id)
    {
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        mongoTemplate.remove(query,instance,getCollectionName());
    }



    public Page findPage(Query query, int index, int size)
    {
        Query query_count=Query.of(query);
        query_count.getSortObject().clear();
        long count=mongoTemplate.count(query_count,instance,getCollectionName());
        query.skip(index).limit(size);
        List<E> list= mongoTemplate.find(query,instance,getCollectionName());
        if(list==null)
            list=new ArrayList<>();
        List<Map<String,Object>> list_map= ContainerUtil.list2map(list);
        Page pg=new Page();
        pg.setRows(list_map);
        pg.setCount((int)count);

        return pg;
    }

    private long count(Query query) {
        Query queryCount = Query.of(query);
        queryCount.getSortObject().clear();
        return mongoTemplate.count(queryCount, instance, getCollectionName());
    }


}