package Model.Statements;

import Model.Containers.MyIDictionary;
import Model.Containers.MyIStack;
import Model.Containers.PrgState;
import Model.Expressions.Exp;

public class IfStmt implements IStmt {
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp e,IStmt t, IStmt el){
        exp=e;
        thenS=t;
        elseS=el;
    }

    @Override
    public	String toString() {
        return "IF("+exp.toString()+") THEN("+thenS.toString()+") ELSE("+elseS.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        MyIStack<IStmt> stack = state.getStk();
        if (exp.eval(symTbl,state.getHeap()) != 0) {
            stack.push(thenS);
        } else {
            stack.push(elseS);
        }

        return null;
    }

}
