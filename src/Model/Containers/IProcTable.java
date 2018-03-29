package Model.Containers;

import java.util.HashMap;

public interface IProcTable<K,V> {
    void add(K name, V pair) throws Exception;
    V get(K name)throws Exception;
    HashMap<K,V> getContent();
}
