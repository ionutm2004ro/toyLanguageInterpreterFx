package Model.Statements;

import Model.Containers.MyIDictionary;
import Model.Containers.PrgState;
import Model.Expressions.Exp;

import Model.Containers.MyFileTable;
import Model.Containers.MyIDictionary;
import Model.Containers.PrgState;
import Model.Expressions.Exp;

public class closeRFile  implements IStmt{
    Exp exp_file_id;

    public closeRFile(Exp fid) {
        exp_file_id = fid;
    }

    @Override
    public String toString() {
        return "closeFile("+exp_file_id.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        state.duplicateStackTop();
        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        MyFileTable fTbl = state.getFileTable();
        Integer fid = exp_file_id.eval(symTbl,state.getHeap());
        fTbl.closeFile(fid);
        return null;
    }
}

