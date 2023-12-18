import java.util.*;

public class TrieUseMap implements Trie{
    class Node {
        TreeMap<Character, Node> child;
        boolean isEndWord;
        String meaning;

        Node() {
            child = new TreeMap<>();
            isEndWord = false;
            meaning = "";
        }

    }
    private Node root = null;

    public TrieUseMap() {
        this.root = new Node();
    }
    public Node getRoot() {
        return root;
    }


    public void insert(String en, String vi) {
        en = en.toLowerCase();
        while(this.contains(en)){
            en = en + ' ';
        }
        Node p = root;
        for (int i = 0; i < en.length(); ++i) {
            if (!p.child.containsKey(en.charAt(i)))
                p.child.put(en.charAt(i), new Node());
            p = p.child.get(en.charAt(i));
        }
        p.isEndWord = true;
        p.meaning = vi;
    }

    public boolean contains(String en) {
        en = en.toLowerCase();
        Node p = root;
        for (int i = 0; i < en.length(); ++i) {
            if (!p.child.containsKey(en.charAt(i)))
                p.child.put(en.charAt(i), new Node());
            p = p.child.get(en.charAt(i));
        }
        return p.isEndWord;
    }

    //    return meaning of word en, if not found return "not found"
    public String translate(String en) {
        en = en.toLowerCase();
        Node p = root;
        for (int i = 0; i < en.length(); ++i) {
            if (!p.child.containsKey(en.charAt(i)))
                p.child.put(en.charAt(i), new Node());
            p = p.child.get(en.charAt(i));
        }
        if (p.isEndWord)
            return p.meaning;
        return "not found";
    }
    public List<Pair<String,String >> suggest(String prefix) {
        prefix = prefix.toLowerCase();
        List<Pair<String,String>> list = new ArrayList<>();
        Node p = endNode(prefix);
        getCandidates(p, prefix, list);
        return list;
    }

    public Node endNode(String prefix) {
        Node p = root;
        for (int i = 0; i < prefix.length(); ++i) {
            if (!p.child.containsKey(prefix.charAt(i)))
                p.child.put(prefix.charAt(i), new Node());
            p = p.child.get(prefix.charAt(i));
        }
        return p;
    }

    private void getCandidates(Node p, String str, List<Pair<String,String>> list) {
        if(p.isEndWord) {
            list.add(new Pair<>(str, p.meaning));
        }
        if(list.size() > 20)
            return;
        p.child.forEach((key, value) -> {
            getCandidates(value, str + key, list);
        });
    }
    public boolean deleteWord(String en) {
        boolean chk = false;
        Node p = root;
        while(this.contains(en)){
            chk |= deleteWord(p, en, 0);
            en = en + ' ';
        }
        return chk;
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

        boolean shouldDeleteChild = deleteWord(current.child.get(en.charAt(depth)), en, depth + 1);

        if (shouldDeleteChild) {
            current.child.remove(en.charAt(depth));
            return isNodeEmpty(current);
        }

        return false;
    }
    private boolean isNodeEmpty(Node node) {
        return node.child.isEmpty();
    }
}
