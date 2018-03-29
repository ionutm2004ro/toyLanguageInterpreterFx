package Model.Expressions;

import Exceptions.MyArithEvalException;
import Model.Containers.MyIDictionary;
import Model.Containers.MyIHeap;

public class rHExp extends Exp{
    String var;

    public rHExp(String v) {
        var = v;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl,MyIHeap<Integer,Integer> heap) throws MyArithEvalException {
        Integer addr = tbl.lookup(var);
        return heap.get(addr);
    }

    @Override
    public String toString() {
        return "rH("+var+")";
    }

}