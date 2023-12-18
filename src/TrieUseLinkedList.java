import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TrieUseLinkedList implements Trie{
    class Node {
        List<Pair<Node, Character>> child;
        boolean isEndWord;
        String meaning;

        Node() {
            child = new LinkedList<>();
            isEndWord = false;
            meaning = "";
        }
    }
    private Node root = null;

    public TrieUseLinkedList() {
        this.root = new Node();
    }

    public Node getRoot() {
        return root;
    }
    private Node findNode(Node p, Character en){
        Node t = null;
        for(Pair<Node, Character> tmp : p.child){
            if(tmp.getSecond() == en){
                t = tmp.getFirst();
                break;
            }
        }
        return t;
    }
    private Pair<Node, Character> findElement(Node p, Character en){
        Pair<Node, Character> ans = null;
        for(Pair<Node, Character> tmp : p.child){
            if(tmp.getSecond() == en){
                ans = tmp;
                break;
            }
        }
        return ans;
    }
    public void insert(String en, String vi) {
        en = en.toLowerCase();
        while(this.contains(en)){
            en = en + ' ';
        }
        Node p = root;
        for (int i = 0; i < en.length(); ++i) {
            Node tmp = findNode(p, en.charAt(i));
            if (tmp == null)
                tmp = new Node();
            p = tmp;
        }
        p.isEndWord = true;
        p.meaning = vi;
    }
    public boolean contains(String en) {
        en = en.toLowerCase();
        Node p = root;
        for (int i = 0; i < en.length(); ++i) {
            Node tmp = findNode(p, en.charAt(i));
            if (tmp == null)
                tmp = new Node();
            p = tmp;
        }
        return p.isEndWord;
    }
    public String translate(String en) {
        en = en.toLowerCase();
        Node p = root;
        for (int i = 0; i < en.length(); ++i) {
            Node tmp = findNode(p, en.charAt(i));
            if (tmp == null)
                tmp = new Node();
            p = tmp;
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
        prefix = prefix.toLowerCase();
        Node p = root;
        for (int i = 0; i < prefix.length(); ++i) {
            Node tmp = findNode(p, prefix.charAt(i));
            if (tmp == null)
                tmp = new Node();
            p = tmp;
        }
        return p;
    }
    private void getCandidates(Node p, String str, List<Pair<String,String>> list) {
        if(p.isEndWord) {
            list.add(new Pair<>(str, p.meaning));
        }
        if(list.size() > 20)
            return;
        for(Pair<Node, Character> tmp : p.child){
            if(tmp.getFirst() != null){
                getCandidates(tmp.getFirst(), str + tmp.getSecond(), list);
            }
        }
    }
    public boolean deleteWord(String en) {
        boolean chk = false;
        while(this.contains(en)){
            chk |= deleteWord(root, en, 0);
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
        Pair<Node, Character> tmp = findElement(current, en.charAt(depth));
        boolean shouldDeleteChild = deleteWord(tmp.getFirst(), en, depth + 1);

        if (shouldDeleteChild) {
//            current.child[index] = null;
            current.child.remove(tmp);
            return isNodeEmpty(current);
        }

        return false;
    }
    private boolean isNodeEmpty(Node node) {
        for(Pair<Node, Character> tmp : node.child){
            if(tmp.getFirst() != null){
                return false;
            }
        }
        return true;
    }
}
