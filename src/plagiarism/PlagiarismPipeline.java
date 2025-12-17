package plagiarism;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class PlagiarismPipeline {

    private final SimilarityMetric metric;

    public PlagiarismPipeline(SimilarityMetric metric) {
        this.metric = metric;
    }

    public List<Document> loadDocuments(String directory) throws IOException {
        List<Document> docs = new ArrayList<>();

        Files.list(Paths.get(directory))
            .filter(f -> f.toString().endsWith(".txt"))
            .forEach(path -> {
                try {
                    String content = Files.readString(path);
                    Map<String, Integer> freq = tokenize(content);
                    docs.add(new Document(path.getFileName().toString(), freq) {});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        return docs;
    }

    private Map<String, Integer> tokenize(String text) {
        Map<String, Integer> freq = new HashMap<>();
        String[] words = text.toLowerCase().split("\\W+");
        for (String word : words) {
            if (word.isEmpty()) continue;
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }
        return freq;
    }

    public double[][] computeSimilarityMatrix(List<Document> docs) {
        int n = docs.size();
        double[][] matrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                double sim = metric.compute(docs.get(i).getWordFreq(), docs.get(j).getWordFreq());
                matrix[i][j] = sim;
                matrix[j][i] = sim;
            }
        }

        return matrix;
    }
}
