package Model.Statements;

import Exceptions.MyArithEvalException;
import Model.Containers.MyDictionary;
import Model.Containers.MyIDictionary;
import Model.Containers.MyStack;
import Model.Containers.PrgState;

import java.util.Stack;

public class ForkStmt implements IStmt {
    IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyArithEvalException, Exception {
        Stack<MyIDictionary<String, Integer>> symtblClone = state.getSymTableClone();
        PrgState newPrg = new PrgState(new MyStack<IStmt>(), symtblClone, state.getOut(), stmt, state.getFileTable(), state.getHeap(),state.getIdCountReference()[0]++,state.getProcTbl(),state.getIdCountReference());
        return newPrg;
    }

    @Override
    public String toString() {
        return "fork("+stmt.toString()+")";
    }

}
