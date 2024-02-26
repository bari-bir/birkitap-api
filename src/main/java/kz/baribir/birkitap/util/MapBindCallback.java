package kz.baribir.birkitap.util;

import java.util.Map;

public interface MapBindCallback {
    void callback(Map<String,Object> result_map, String key, Object obj);
}
