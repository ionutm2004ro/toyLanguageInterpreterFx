package Model.Statements;

import Model.Containers.MyIStack;
import Model.Containers.PrgState;

import java.util.ArrayList;
import java.util.Collections;

public class StmtArray implements IStmt{
    ArrayList<IStmt> stmtList;

    public StmtArray() {
        stmtList=new ArrayList<IStmt>();
    }

    public void add(IStmt stmt){
        stmtList.add(stmt);
    }

    @Override
    public String toString() {
        String arr = "{";
        for(IStmt e:stmtList){
            arr+=e.toString()+";";
        }

        arr=arr.substring(0,arr.length() - 1)+"}";
        return arr;
    }

    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk= state.getStk();
        Collections.reverse(stmtList);
        for(IStmt e:stmtList){
            stk.push(e);
        }
        return null;
    }
}