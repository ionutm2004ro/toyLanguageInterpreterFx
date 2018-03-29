package Model.Statements;

import Model.Containers.MyIDictionary;
import Model.Containers.MyIStack;
import Model.Containers.PrgState;
import Model.Expressions.Exp;

public class WhileStmt implements IStmt{
    Exp expr;
    IStmt stmt;

    public WhileStmt(Exp e,IStmt s) {
        expr=e;
        stmt=s;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        MyIStack<IStmt> stack = state.getStk();
        if (expr.eval(symTbl,state.getHeap()) != 0) {
            stack.push(new WhileStmt(expr, stmt));
            stack.push(stmt);
        }
        return null;
    }

    @Override
    public String toString() {
        return "while("+expr.toString()+"){"+stmt.toString()+"}";
    }

}
