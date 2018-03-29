package Model.Expressions;

import Exceptions.MyArithEvalException;
import Model.Containers.MyIDictionary;
import Model.Containers.MyIHeap;

public class ArithExp extends Exp {
    Exp e1;
    Exp e2;
    char op;

    public ArithExp(char operation,Exp ex1, Exp ex2){
        op = operation;
        e1 = ex1;
        e2 = ex2;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl,MyIHeap<Integer,Integer> heap) throws Exception {
        if(op=='+')return e1.eval(tbl,heap)+e2.eval(tbl,heap);
        if(op=='-')return e1.eval(tbl,heap)-e2.eval(tbl,heap);
        if(op=='*')return e1.eval(tbl,heap)*e2.eval(tbl,heap);
        if(op=='/') {
            if(e2.eval(tbl,heap)==0) {
                throw new MyArithEvalException("Divide by zero: "+e1.toString()+op+e2.toString());
            }
            return e1.eval(tbl,heap)/e2.eval(tbl,heap);
        }
        throw new MyArithEvalException("Wrong operation Symbol! : \"" + op + "\"");
    }

    @Override
    public String toString() {
        return e1.toString()+op+e2.toString();
    }

}