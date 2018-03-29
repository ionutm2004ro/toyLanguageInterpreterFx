package Model.Containers;

import java.util.HashMap;

public interface MyIDictionary<K,V> {

    boolean isDefined(K id);

    void update(K id, V val);

    void add(K id, V val);

    V lookup(K id);

    String toString();

    boolean hasValue(V val);

    HashMap<K,V> getContent();

    HashMap<K,V> clone();
}
