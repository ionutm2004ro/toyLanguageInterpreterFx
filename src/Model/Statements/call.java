package Model.Statements;

import Exceptions.MyArithEvalException;
import Model.Containers.*;
import Model.Expressions.Exp;

import java.util.List;

public class call implements IStmt{
    String functName;
    List<Exp> expList;

    public call(String fName, List<Exp> exprList){
        functName = fName;
        expList = exprList;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        state.duplicateStackTop();
        IProcTable<String,Pair<List<String>,IStmt>> tmpProcTbl=state.getProcTbl();
        MyIDictionary<String,Integer> tmpSymTbl = state.getSymTable();
        try {
            Pair<List<String>, Model.Statements.IStmt> p = tmpProcTbl.get(functName);
            //System.out.println(p.toString());
            int i=0;
            for(String vName:p.left()){
                tmpSymTbl.add(vName,expList.get(i).eval(state.getSymTable(),state.getHeap()));
                i++;
            }
            state.getStk().push(new returnStmt());
            state.getStk().push(p.right());
        }
        catch (Exception e){
            throw new Exception("Could not execute "+ functName + " with the expressions " + expList.toString());
        }
        return null;
    }

    @Override
    public String toString(){
        return "call "+functName;
    }
}
