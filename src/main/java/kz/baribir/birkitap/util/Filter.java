package kz.baribir.birkitap.util;

import java.util.Map;

public interface Filter {
    boolean operation(Map<String, Object> item);
}