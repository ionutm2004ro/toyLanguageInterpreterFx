package Model.Statements;

import Model.Containers.MyIStack;
import Model.Containers.PrgState;

public class CompStmt implements IStmt{
    IStmt first;
    IStmt snd;

    public CompStmt(IStmt first, IStmt snd) {
        this.first = first;
        this.snd = snd;
    }

    @Override
    public String toString() {
        return "("+first.toString()+";"+snd.toString()+")";
    }

    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk= state.getStk();
        stk.push(snd);
        stk.push(first);
        return null;
    }
}