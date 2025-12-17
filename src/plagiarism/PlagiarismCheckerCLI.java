package plagiarism;

import java.nio.file.*;
import java.util.*;

public class PlagiarismCheckerCLI {

    private static Scanner sc = new Scanner(System.in);
    private static Path submissionsDir;

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== Plagiarism Checker Menu =====");
            System.out.println("1. Set submissions directory");
            System.out.println("2. List all text files");
            System.out.println("3. Compute similarity");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            switch (sc.nextLine()) {
                case "1": setDirectory(); break;
                case "2": listFiles(); break;
                case "3": computeSimilarity(); break;
                case "4": System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void setDirectory() {
        System.out.print("Enter submissions directory: ");
        submissionsDir = Paths.get(sc.nextLine());
        if (!Files.isDirectory(submissionsDir)) {
            System.out.println("Invalid directory!");
            submissionsDir = null;
        }
    }

    private static void listFiles() {
        if (submissionsDir == null) return;
        try {
            Files.list(submissionsDir)
                 .filter(f -> f.toString().endsWith(".txt"))
                 .forEach(f -> System.out.println(f.getFileName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void computeSimilarity() {
        if (submissionsDir == null) return;

        try {
            PlagiarismPipeline pipeline =
                    new PlagiarismPipeline(new CosineMetric());

            List<Document> docs =
                    pipeline.loadDocuments(submissionsDir.toString());

            double[][] matrix =
                    pipeline.computeSimilarityMatrix(docs);

            // PRINT
            System.out.println("\n===== Similarity Matrix =====");
            printMatrix(docs, matrix);

            // SAVE TO FILE
            saveMatrixToFile(docs, matrix);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printMatrix(List<Document> docs, double[][] m) {
        System.out.print("        ");
        for (Document d : docs)
            System.out.print(d.getName() + "   ");
        System.out.println();

        for (int i = 0; i < m.length; i++) {
            System.out.print(docs.get(i).getName() + "   ");
            for (int j = 0; j < m[i].length; j++)
                System.out.printf("%.2f      ", m[i][j]);
            System.out.println();
        }
    }

    private static void saveMatrixToFile(List<Document> docs, double[][] m)
            throws Exception {

        Path output = Paths.get("similarity_matrix.csv");

        StringBuilder sb = new StringBuilder();

        
        sb.append(",");
        for (Document d : docs) {
            sb.append(d.getName()).append(",");
        }
        sb.append("\n");

       
        for (int i = 0; i < m.length; i++) {
            sb.append(docs.get(i).getName()).append(",");
            for (int j = 0; j < m[i].length; j++) {
                sb.append(String.format("%.2f", m[i][j])).append(",");
            }
            sb.append("\n");
        }

        Files.writeString(output, sb.toString());

        System.out.println("✔ similarity_matrix.csv created");
    }
}
