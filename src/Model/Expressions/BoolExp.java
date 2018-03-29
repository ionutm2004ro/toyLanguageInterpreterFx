package Model.Expressions;

import java.util.HashMap;
import java.util.function.Supplier;

import Model.Containers.MyIDictionary;
import Model.Containers.MyIHeap;

public class BoolExp extends Exp{
    Exp exp1,exp2;
    String operator;
    int a,b;
    HashMap<String, Supplier> op;

    public BoolExp(Exp e1,String o, Exp e2) {
        exp1=e1;
        operator=o;
        exp2=e2;
        this.op = new HashMap<>();
        op.put("<",  () ->(a<b));
        op.put("<=", () ->(a<=b));
        op.put("==", () ->(a==b));
        op.put("!=", () ->(a!=b));
        op.put(">",  () ->(a>b));
        op.put(">=", () ->(a>=b));
    }

    @Override
    public int eval(
            MyIDictionary<String, Integer> tbl,
            MyIHeap<Integer, Integer> heap) throws Exception {
        //0 is false, otherwise true
        a = exp1.eval(tbl, heap);
        b = exp2.eval(tbl, heap);
        if (!op.containsKey(operator))
            throw new Exception("operator "+operator+" does not exist");
        return (boolean) op.get(operator).get() ? 1:0;
    }

    @Override
    public String toString() {
        return "("+exp1.toString()+operator+exp2.toString()+")";
    }

}