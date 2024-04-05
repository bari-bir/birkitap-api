package kz.baribir.birkitap.algorithm;

import java.util.List;

public interface RecommendationAlgorithm<T> {

    List<T> execute(String userId);
}
