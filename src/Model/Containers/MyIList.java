package Model.Containers;


import java.util.ArrayList;

public interface MyIList<T> {
    void add(T v);
    ArrayList<T> getContainer();
    T get(int current);
    String toString();
}
