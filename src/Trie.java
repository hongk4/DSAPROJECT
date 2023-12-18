import java.util.List;

public interface Trie {
    void insert(String en, String vi);
    boolean contains(String en);
    String translate(String en);
    List<Pair<String,String >> suggest(String prefix);
    boolean deleteWord(String en);
}
