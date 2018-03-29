package Model.Containers;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T>{
    ArrayList<T> list;

    public MyList() {
        list = new ArrayList<T>();
    }

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public ArrayList<T> getContainer() {
        return list;
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    public String toString() {
        String stringedList="";
        int index=0;
        while(index<list.size()) {
            stringedList+=list.get(index).toString()+"\n";
            index++;
        }
        return stringedList;//.substring(0, Math.max(0,stringedList.length() - 1));
    }

}