package kz.baribir.birkitap.bean;

import jakarta.annotation.Resource;
import kz.baribir.birkitap.model.Page;
import kz.baribir.birkitap.util.ContainerUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class MongoRepositoryBase<E> {


    @Resource
    protected MongoTemplate mongoTemplate;



    public void save(E e)
    {
        mongoTemplate.save(e,e.getClass().getSimpleName());
    }

    public E findById(String id,Class<E> clazz)
    {

        return mongoTemplate.findById(id,clazz,clazz.getSimpleName());
    }
    public long count(Query query, Class<E> clazz) {
        return mongoTemplate.count(query, clazz, clazz.getSimpleName());
    }

    public List<E> find(Query query, Class<E> clazz)
    {

        return mongoTemplate.find(query,clazz, clazz.getSimpleName());
    }


    public List<E> findAll(Class<E> clazz)
    {
        return mongoTemplate.findAll(clazz, clazz.getSimpleName());
    }

    public void deleteById(String id,Class<E> clazz)
    {
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        mongoTemplate.remove(query,clazz,clazz.getSimpleName());

    }



    public Page findPage(Query query, Class<E> clazz, int index, int size)
    {
        Query query_count=Query.of(query);
        query_count.getSortObject().clear();
        long count=mongoTemplate.count(query_count,clazz,clazz.getSimpleName());
        query.skip(index).limit(size);
        List<E> list= mongoTemplate.find(query,clazz,clazz.getSimpleName());
        if(list==null)
            list=new ArrayList<>();
        List<Map<String,Object>> list_map= ContainerUtil.list2map(list);
        Page pg=new Page();
        pg.setRows(list_map);
        pg.setRecordcount((int)count);

        return pg;
    }

    protected Page findPageByQueryParam(Query query, Class<E> clazz, CustomQueryParam queryParam){
        if(queryParam.getLength() <= 0 || queryParam.getLength() > 200){
            queryParam.setLength(200);
        }
        if(queryParam.getStart() < 0){
            queryParam.setStart(0);
        }

        queryParam.getFilter().forEach((key, value) -> {
            if(queryParam.getParam2column().get(key) != null && value != null && !("".equals(value.toString().trim()))){
                query.addCriteria(Criteria.where(queryParam.getParam2column().get(key)).is(value));
            }
        });
        queryParam.getKeyWordMap().forEach((key, value) -> {
            if(queryParam.getParam2column().get(key) != null && value != null && !("".equals(value.toString().trim()))){
                query.addCriteria(Criteria.where(queryParam.getParam2column().get(key)).regex(value.toString()));
            }
        });
        queryParam.getTime().forEach(customTime -> {
            String time_name = queryParam.getParam2column().get(customTime.getKey());
            Criteria criteria = null;
            if(time_name != null && customTime.getMin() != 0){
                criteria = Criteria.where(time_name).gte(new Date(customTime.getMin()));
            }
            if(time_name != null && customTime.getMax() != 0){
                if(criteria == null)
                    criteria = Criteria.where(time_name).lte(new Date(customTime.getMax()));
                else
                    criteria.lte(new Date(customTime.getMax()));
            }
            if(criteria != null) query.addCriteria(criteria);
        });
        queryParam.getSort().forEach(customSort -> {
            String colName = queryParam.getParam2column().get(customSort.getKey());
            Sort.Direction direction = (customSort.getIsAsc() == 0) ? Sort.Direction.DESC : Sort.Direction.ASC;
            if(colName != null){
                query.with(Sort.by(direction, colName));
            }
        });

        return findPage(query, clazz, queryParam.getStart(), queryParam.getLength());
    }
    public List<Map> getResults(Aggregation aggregation, Class<E> clazz){
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, clazz.getSimpleName(), Map.class);
        return results.getMappedResults();
    }
    public Map getResult(Aggregation aggregation, Class<E> clazz){
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, clazz.getSimpleName(), Map.class);
        return results.getUniqueMappedResult();
    }

    public E findOne(Query query, Class<E> clazz){
        return mongoTemplate.findOne(query, clazz, clazz.getSimpleName());
    }

    public Map findOne(List<AggregationOperation> aggregationOperations, Class<E> clazz){
        AggregationOperation[] operations = new AggregationOperation[aggregationOperations.size()];
        for (int i = 0; i < aggregationOperations.size(); i++) {
            operations[i] = aggregationOperations.get(i);
        }
        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, clazz.getSimpleName(), Map.class);
        return results.getUniqueMappedResult();
    }

}
