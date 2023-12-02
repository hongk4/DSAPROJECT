public class TestTrie {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple", "Quả táo");
        trie.insert("app", "ung dung");
        trie.insert("application", "ung dung");
        trie.insert("banana", "Quả chuối");
        trie.insert("pine apple", "Quả dứa");
//        System.out.println(trie.translate("banana"));
        trie.print();
        System.out.println(trie.suggest("ap"));
    }
}
