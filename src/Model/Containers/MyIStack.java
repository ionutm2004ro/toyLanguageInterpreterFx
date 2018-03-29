package Model.Containers;

import java.util.Stack;

public interface MyIStack<T> {
    T pop();
    void push(T v);
    boolean isEmpty();
    String toString();
    Stack<T> getContent();
}