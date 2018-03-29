package Model.Containers;

import java.util.HashMap;
import java.util.Map.Entry;

public class MyHeap<K,V> implements MyIHeap<K,V>{
    HashMap<K,V> heap;
    Integer count;

    public MyHeap(){
        heap = new HashMap<K,V>();
        count=0;
    }

    @Override
    public void add(K addr,V val) throws Exception {
        if(heap.containsKey(addr)) {
            throw new Exception("address already allocated");
        }
        heap.put(addr,val);
        count++;
    }

    @Override
    public void update(K addr, V val) throws Exception {
        if(!heap.containsKey(addr)) {
            throw new Exception("address not allocated");
        }
        heap.replace(addr,val);
    }

    @Override
    public boolean isDefined(K addr) {
        return heap.containsKey(addr);
    }

    @Override
    public Integer getCount() {
        return count;
    }

    @Override
    public V get(K addr) {
        return heap.get(addr);
    }

    @Override
    public String toString() {
        String stringDict="";
        for (Entry<K, V> e : heap.entrySet()) {
            stringDict += "("+e.getKey().toString()+"->";
            stringDict += e.getValue().toString()+")\n";
        }
        return stringDict;//.substring(0, Math.max(0,stringDict.length() - 1));
    }

    @Override
    public HashMap<K, V> getContent() {
        return heap;
    }

    @Override
    public void setContent(HashMap<K, V> cont) {
        heap=cont;
    }

}