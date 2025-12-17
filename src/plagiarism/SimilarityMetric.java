package plagiarism;

import java.util.Map;

public interface SimilarityMetric {
    double compute(Map<String, Integer> a, Map<String, Integer> b);
}
