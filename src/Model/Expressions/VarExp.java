package Model.Expressions;

import Model.Containers.MyIDictionary;
import Model.Containers.MyIHeap;

public class VarExp extends Exp {
    String id;

    public VarExp(String i){
        id = i;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl,MyIHeap<Integer,Integer> heap) {
        return tbl.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }

}