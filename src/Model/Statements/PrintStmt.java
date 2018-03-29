package Model.Statements;

import Model.Containers.MyIDictionary;
import Model.Containers.MyIList;
import Model.Containers.PrgState;
import Model.Expressions.Exp;

public class PrintStmt implements IStmt {
    Exp exp;

    public PrintStmt(Exp e){
        exp = e;
    }

    @Override
    public String toString() {
        return "print("+exp.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIList<Integer> out = state.getList();
        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        out.add(exp.eval(symTbl,state.getHeap()));
        return null;
    }

}
