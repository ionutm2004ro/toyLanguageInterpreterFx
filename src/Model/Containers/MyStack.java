package Model.Containers;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    Stack<T> stack;

    public MyStack() {
        stack = new Stack<T>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public String toString() {
        String stringedStack="";
        Stack<T> tempstack=(Stack<T>) stack.clone();
        while(!tempstack.isEmpty()) {
            stringedStack+=tempstack.pop().toString()+"\n";
        }
        return stringedStack;//.substring(0, Math.max(0,stringedStack.length() - 1));
    }

    public Stack<T> getContent(){
        return(Stack<T>)stack.clone();
    }

}