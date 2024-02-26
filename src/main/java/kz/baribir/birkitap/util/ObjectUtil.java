package kz.baribir.birkitap.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectUtil {

    public static <O> O clone(O o, Class<O> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            O deepCopy = objectMapper
                    .readValue(objectMapper.writeValueAsString(o), clazz);
            return deepCopy;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;
        return obj.toString().trim().isEmpty();
    }
}
