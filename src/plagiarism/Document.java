package plagiarism;

import java.util.Map;

public abstract class Document {
    protected String name;
    protected Map<String, Integer> wordFreq;

    public Document(String name, Map<String, Integer> wordFreq) {
        this.name = name;
        this.wordFreq = wordFreq;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getWordFreq() {
        return wordFreq;
    }
}
