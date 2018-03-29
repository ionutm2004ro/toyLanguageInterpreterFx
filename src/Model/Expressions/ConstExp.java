package Model.Expressions;

import Model.Containers.MyIDictionary;
import Model.Containers.MyIHeap;

public class ConstExp extends Exp {
    int number;

    public ConstExp(int number) {
        this.number = number;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl,MyIHeap<Integer,Integer> heap) {
        return number;
    }

    @Override
    public String toString() {
        return new Integer(number).toString();
    }
}
