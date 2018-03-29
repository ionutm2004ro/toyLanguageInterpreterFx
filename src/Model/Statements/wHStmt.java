package Model.Statements;

import Exceptions.MyArithEvalException;
import Model.Containers.MyIDictionary;
import Model.Containers.MyIHeap;
import Model.Containers.PrgState;
import Model.Expressions.Exp;

public class wHStmt implements IStmt {
    String var;
    Exp expr;

    public wHStmt(String v,Exp e) {
        var=v;
        expr=e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyArithEvalException, Exception {
        MyIDictionary<String, Integer> tbl = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        heap.update(tbl.lookup(var), expr.eval(tbl, heap));
        return null;
    }

    @Override
    public String toString() {
        return "wH("+var+","+expr.toString()+")";
    }

}
