package Model.Containers;

import java.util.HashMap;
import java.util.Map;

public class ProcTable<K,V> implements IProcTable<K,V> {
    HashMap<K,V> table;

    public ProcTable(){
        table = new HashMap<>();
    }

    @Override
    public void add(K name, V pair) throws Exception {
        if(table.containsKey(name)){
            throw new Exception("Procedure \""+name+"\" already exists!");
        }
        table.put(name,pair);
    }

    @Override
    public HashMap<K, V> getContent() {
        return table;
    }

    @Override
    public V get(K name) throws Exception {
        return table.get(name);
    }

    @Override
    public String toString(){
        String tmpStr="";
        for(Map.Entry<K, V> e : table.entrySet()){
            tmpStr += "("+e.getKey().toString()+"->";
            tmpStr += e.getValue().toString()+")\n";
        }
        return tmpStr;
    }
}
