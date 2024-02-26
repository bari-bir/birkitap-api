package kz.baribir.birkitap.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FreemarkerTemplateContent {
    private String content = "";
    private Map<String, Object> urlMap = new HashMap<>();
}
