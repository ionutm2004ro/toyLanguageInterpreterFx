package Model.Containers;

import java.util.HashMap;
import java.util.Map.Entry;

public class MyDictionary<K,V> implements MyIDictionary<K,V> {
    HashMap<K,V> dict;

    public MyDictionary() {
        dict = new HashMap<K,V>();
    }

    public MyDictionary(HashMap<K,V> map) {
        dict = map;
    }
    @Override
    public boolean isDefined(K id) {
        return dict.containsKey(id);
    }

    @Override
    public boolean hasValue(V val) {
        return dict.containsValue(val);
    }

    @Override
    public void update(K id, V val) {
        dict.replace(id, val);
    }

    @Override
    public void add(K id, V val) {
        dict.put(id, val);
    }

    @Override
    public V lookup(K id) {
        return dict.get(id);
    }

    public String toString() {
        String stringDict="";
        for (Entry<K, V> e : dict.entrySet()) {
            stringDict += "("+e.getKey().toString()+":";
            stringDict += e.getValue().toString()+")\n";
        }
        return stringDict;//.substring(0, Math.max(0,stringDict.length() - 1));
    }

    @Override
    public HashMap<K, V> getContent() {
        return dict;
    }

    public HashMap<K,V> clone(){
        return (HashMap<K, V>) dict.clone();
    }
}