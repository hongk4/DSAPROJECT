public class Pair<K,E> {
    private K first;
    private E second;

    public Pair(K key, E value) {
        this.first = key;
        this.second = value;
    }

    public K getFirst() {
        return first;
    }

    public E getSecond() {
        return second;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public void setSecond(E second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "{" + first + ", " + second + "}";
    }
}

