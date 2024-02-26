package kz.baribir.birkitap.bean;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.baribir.birkitap.exception.BadRequestException;
import kz.baribir.birkitap.util.PojUtil;
import lombok.Data;

import java.util.*;

@Data
public class CustomQueryParam {
    private Map<String,Object> keyWordMap=new HashMap<>();
    private List<String> groupby = new ArrayList<>();
    private int start;
    private int length;
    private List<CustomSort> sort=new ArrayList<>();
    private List<CustomTime> time=new ArrayList<>();
    private int isSimple=0;
    private Map<String,Object> filter=new HashMap<>();
    private Map<String, String> param2column = new HashMap<>();

    public static  CustomQueryParam parse(Map<String,Object> param) {
        if(param == null)
            return null;
        try {
            CustomQueryParam customQueryParam = new CustomQueryParam();
            Set<String> keys = param.keySet();
            for (String key : keys) {
                Object value = param.get(key);
                switch (key) {
                    case "start": {
                        if (value == null)
                            customQueryParam.setStart(0);
                        else
                            customQueryParam.setStart(Integer.parseInt(value.toString().trim()));
                    }
                    break;
                    case "length": {
                        if (value == null)
                            customQueryParam.setLength(0);
                        else
                            customQueryParam.setLength(Integer.parseInt(value.toString().trim()));
                    }
                    break;
                    case "sort": {
                        List<CustomSort> customSorts = new ObjectMapper().convertValue(value, new TypeReference<List<CustomSort>>() {
                        });
                        if (customSorts == null)
                            customSorts = new ArrayList<>();
                        customQueryParam.setSort(customSorts);
                    }
                    break;
                    case "time": {
                        List<CustomTime> customTimes = new ObjectMapper().convertValue(value, new TypeReference<List<CustomTime>>() {
                        });
                        if (customTimes == null)
                            customTimes = new ArrayList<>();
                        customQueryParam.setTime(customTimes);
                    }
                    break;
                    case "isSimple": {
                        if (value == null)
                            customQueryParam.setIsSimple(0);
                        else
                            customQueryParam.setIsSimple(Integer.parseInt(value.toString().trim()));
                    }
                    break;
                    case "filter": {
                        Map<String, Object> filter_map = new ObjectMapper().convertValue(value, new TypeReference<Map<String, Object>>() {
                        });
                        if (filter_map == null)
                            filter_map = new HashMap<>();
                        customQueryParam.setFilter(filter_map);
                    }
                    break;
                    case "groupby": {
                        List<String> groupby = new ObjectMapper().convertValue(value, new TypeReference<List<String>>() {
                        });
                        if (groupby == null)
                            groupby = new ArrayList<>();
                        customQueryParam.setGroupby(groupby);
                    }
                    break;
                    default: {
                        customQueryParam.getKeyWordMap().put(key, value);
                    }
                    break;
                }

            }

            return customQueryParam;
        }
        catch (Exception exception) {
            throw new BadRequestException("Pagination query format not supported! " + PojUtil.getStackTrace(exception));
        }
    }
}
