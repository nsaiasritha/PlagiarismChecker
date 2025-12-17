package plagiarism;

import java.util.List;
import java.util.Scanner;

public class PlagiarismChecker {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PlagiarismPipeline pipeline = new PlagiarismPipeline(new CosineMetric());

        System.out.print("Enter directory containing text files: ");
        String dir = sc.nextLine();

        List<Document> docs = pipeline.loadDocuments(dir);
        double[][] matrix = pipeline.computeSimilarityMatrix(docs);

        System.out.println("\nSimilarity Matrix:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%.2f\t", matrix[i][j]);
            }
            System.out.println();
        }

        sc.close();
    }
}
