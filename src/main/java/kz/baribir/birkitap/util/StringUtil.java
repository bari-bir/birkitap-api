package kz.baribir.birkitap.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringUtil {

    private StringUtil() {}

    public static boolean inarray(String src, String[] array) {
        for (int i = 0; i < array.length; ++i)
            if (src.equals(array[i]))
                return true;
        return false;
    }

    public static String toString(Object obj) {
        if (obj == null)
            return "null";
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
