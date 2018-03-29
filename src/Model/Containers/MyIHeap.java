package Model.Containers;

import java.util.HashMap;

public interface MyIHeap<K,V>{
    void add(K addr, V val) throws Exception;
    void update(K addr, V val) throws Exception;
    boolean isDefined(K addr);
    V get(K addr);
    Integer getCount();
    HashMap<K,V> getContent();
    void setContent(HashMap<K,V> cont);
}
