package kz.baribir.birkitap.util;


import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.exception.BadRequestException;

import java.util.Map;

public class ParamUtil {
    public static String get_string(Map<String, Object> params, String key, boolean allow_null, boolean needTrim) {
        Object val = params.get(key);
        if (!allow_null && val == null)
            throw new BadRequestException(" Request param " + key + " can't be null!");
        if (val == null)
            return null;
        if (needTrim)
            return val.toString().trim();
        return val.toString();
    }

    public static String get_string(Map<String, Object> params, String key, boolean allow_null) {
        Object val = params.get(key);
        if (!allow_null && val == null)
            throw new BadRequestException(" Request param " + key + " can't be null!");
        if (val == null)
            return null;
        return val.toString();
    }

    public static float get_float(Map<String, Object> params, String key, boolean allow_null, float default_value){
        Object val = params.get(key);
        if (!allow_null && val == null)
            throw new BadRequestException(" Request param " + key + " can't be null!");
        if(val == null)
            return default_value;
        try{
            return Float.parseFloat(val.toString().trim());
        }catch (Exception e){
            throw new BadRequestException("number format is not correct");
        }

    }

    public static double get_double(Map<String, Object> params, String key, boolean allow_null, double default_value){
        Object val = params.get(key);
        if (!allow_null && val == null)
            throw new BadRequestException(" Request param " + key + " can't be null!");
        if(val == null)
            return default_value;
        try{
            return Double.parseDouble(val.toString().trim());
        }catch (Exception e){
            throw new BadRequestException("number format is not correct");
        }

    }

    public static long get_long(Map<String, Object> params, String key, boolean allow_null, long default_value){
        Object val = params.get(key);
        if (!allow_null && val == null)
            throw new BadRequestException(" Request param " + key + " can't be null!");
        if(val == null)
            return default_value;
        try{
            return Long.parseLong(val.toString().trim());
        }catch (Exception e){
            throw new BadRequestException("number format is not correct");
        }

    }

    public static int get_int(Map<String, Object> params, String key, boolean allow_null){
        Object val = params.get(key);
        if (!allow_null && val == null)
            throw new BadRequestException("Request param " + key + " can't be null!");
        if(val == null) return 0;
        return Integer.parseInt(val.toString());
    }

    public static TokenInfo getTokenInfo(HttpServletRequest request) {
        Map<String, String> jwt_payload = (Map<String, String>) request.getAttribute("jwt_payload");
        if (jwt_payload == null)
            return null;
        String username = jwt_payload.get("username");
        String uuid = jwt_payload.get("uuid");
        String type = jwt_payload.get("type");

        return new TokenInfo(username, type, uuid);
    }
}
