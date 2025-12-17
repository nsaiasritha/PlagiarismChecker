package plagiarism;

import java.util.Map;

public class CosineMetric implements SimilarityMetric {

    @Override
    public double compute(Map<String, Integer> a, Map<String, Integer> b) {
        double dot = 0, magA = 0, magB = 0;

        for (String key : a.keySet()) {
            int valA = a.getOrDefault(key, 0);
            int valB = b.getOrDefault(key, 0);
            dot += valA * valB;
            magA += valA * valA;
        }

        for (int val : b.values()) {
            magB += val * val;
        }

        if (magA == 0 || magB == 0) return 0;
        return dot / (Math.sqrt(magA) * Math.sqrt(magB));
    }
}
