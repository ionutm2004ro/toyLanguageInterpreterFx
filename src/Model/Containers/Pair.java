package Model.Containers;

public class Pair<K,V> {
    K left;
    V right;

    public Pair(K l,V r) {
        left=l;
        right=r;
    }

    public K left(){
        return left;
    }

    public V right(){
        return right;
    }

    @Override
    public String toString() {
        return "("+left.toString()+","+right.toString()+")";
    }
}