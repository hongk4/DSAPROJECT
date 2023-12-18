import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class TrieUseArrayList implements Trie{
    class Node {
        List<Node> child;
        boolean isEndWord;
        String meaning;

        Node() {
            child = new ArrayList<>();
            for (int i = 0; i < 27; i++) {
                child.add(null);
            }
            isEndWord = false;
            meaning = "";
        }
    }

    private Node root = null;
    private HashSet<String> tmp = new HashSet<>();

    public TrieUseArrayList() {
        this.root = new Node();
    }

    public Node getRoot() {
        return root;
    }

    public void insert(String en, String vi) {
        en = en.toLowerCase();
        while (tmp.contains(en)) {
            en = en + ' ';
        }
        tmp.add(en);
        Node p = root;
        for (int i = 0; i < en.length(); ++i) {
            int x = charToInt(en.charAt(i));
            if (x < 0 || x >= 27) continue;
            if (p.child.get(x) == null)
                p.child.set(x, new Node());
            p = p.child.get(x);
        }
        p.isEndWord = true;
        p.meaning = vi;
    }

    public boolean contains(String en) {
        en = en.toLowerCase();
        Node p = root;
        for (int i = 0; i < en.length(); ++i) {
            int x = charToInt(en.charAt(i));
            if (p.child.get(x) == null)
                p.child.set(x, new Node());
            p = p.child.get(x);
        }
        return p.isEndWord;
    }

    public String translate(String en) {
        en = en.toLowerCase();
        Node p = root;
        for (int i = 0; i < en.length(); ++i) {
            int x = charToInt(en.charAt(i));
            if (p.child.get(x) == null)
                p.child.set(x, new Node());
            p = p.child.get(x);
        }
        if (p.isEndWord)
            return p.meaning;
        return "not found";
    }

    public boolean deleteWord(String en) {
        return deleteWord(root, en, 0);
    }

    private boolean deleteWord(Node current, String en, int depth) {
        if (current == null) {
            return false;
        }

        if (depth == en.length()) {
            if (!current.isEndWord) {
                return false;
            }
            current.isEndWord = false;
            return isNodeEmpty(current);
        }

        int index = charToInt(en.charAt(depth));
        boolean shouldDeleteChild = deleteWord(current.child.get(index), en, depth + 1);

        if (shouldDeleteChild) {
            current.child.set(index, null);
            return isNodeEmpty(current);
        }

        return false;
    }
    private boolean isNodeEmpty(Node node) {
        for (Node childNode : node.child) {
            if (childNode != null) {
                return false;
            }
        }
        return true;
    }
    
    public List<Pair<String, String>> suggest(String prefix) {
        prefix = prefix.toLowerCase();
        List<Pair<String, String>> list = new ArrayList<>();
        Node p = endNode(prefix);
        getCandidates(p, prefix, list);
        return list;
    }

    public Node endNode(String prefix) {
        prefix = prefix.toLowerCase();
        Node p = root;
        for (int i = 0; i < prefix.length(); ++i) {
            int x = charToInt(prefix.charAt(i));
            if (p.child.get(x) == null)
                p.child.set(x, new Node());
            p = p.child.get(x);
        }
        return p;
    }

    private void getCandidates(Node p, String str, List<Pair<String, String>> list) {
        if (p.isEndWord) {
            list.add(new Pair<>(str, p.meaning));
        }
        if (list.size() > 20)
            return;
        for (int i = 0; i < 27; ++i) {
            if (p.child.get(i) != null) {
                char ch = intToChar(i);
                getCandidates(p.child.get(i), str + ch, list);
            }
        }
    }

    private int charToInt(char c) {
        if (c == ' ')
            return 26;
        return (int) (c - 'a');
    }

    private char intToChar(int x) {
        if (x == 26)
            return (char) (' ');
        return (char) (x + 'a');
    }
}
