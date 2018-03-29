package Model.Statements;

import Model.Containers.MyIDictionary;
import Model.Containers.PrgState;
import Model.Expressions.Exp;

public class AssignStmt implements IStmt {
    String id;
    Exp exp;

    public AssignStmt(String id,Exp exp) {
        this.id = id;
        this.exp=exp;
    }

    @Override
    public String toString() {
        return id+"="+exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        //MyIStack<IStmt> stk = state.getStk();
        state.duplicateStackTop();
        MyIDictionary<String,Integer> symTbl= state.getSymTable();
        int val = exp.eval(symTbl,state.getHeap());
        if (symTbl.isDefined(id))
            symTbl.update(id,val);
        else symTbl.add(id,val);

        return null;
    }

}
