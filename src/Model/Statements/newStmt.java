package Model.Statements;

import Model.Containers.MyIDictionary;
import Model.Containers.MyIHeap;
import Model.Containers.PrgState;
import Model.Expressions.Exp;

public class newStmt implements IStmt{
    String var;
    Exp expr;

    public newStmt(String v,Exp e) {
        var=v;
        expr=e;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        state.duplicateStackTop();
        MyIDictionary<String, Integer> symtbl = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        Integer addr = heap.getCount()+1;
        heap.add(addr,expr.eval(symtbl,heap));
        symtbl.add(var, addr);
        return null;
    }

    public String toString() {
        return "new("+var+","+expr.toString()+")";
    }

}

