package kz.baribir.birkitap.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Page {

    private int count = 0;
    private Object customInfo = null;
    private List<Map<String, Object>> rows = new ArrayList<>();

    public void setRecordcount(int count) {
    }
}
