package Model.Statements;

import java.io.IOException;

import Exceptions.MyArithEvalException;
import Model.Containers.MyFileTable;
import Model.Containers.MyIDictionary;
import Model.Containers.PrgState;
import Model.Expressions.Exp;

public class readFile implements IStmt{
    Exp exp_file_id;
    String var_name;

    public readFile(Exp fid,String var) {
        exp_file_id = fid;
        var_name=var;
    }

    @Override
    public String toString() {
        return "readFile("+exp_file_id.toString()+","+var_name+")";
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        state.duplicateStackTop();
        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        MyFileTable fTbl = state.getFileTable();
        int fid;
        try {
            fid = exp_file_id.eval(symTbl,state.getHeap());
            if(!fTbl.isDefined(fid))
                System.out.println("File does not exist");
            String line;
            try {
                line = fTbl.lookup(fid).right().readLine();
                if (line==null)
                    line="0";
                Integer val = Integer.parseInt(line);
                if (symTbl.isDefined(var_name))
                    symTbl.update(var_name,val);
                else symTbl.add(var_name,val);
            } catch (IOException e) {
                System.out.print("readline went bad ");
                e.printStackTrace();
            }
        } catch (MyArithEvalException e) {
            System.out.println("arith eval exception");
            e.printStackTrace();
        }
        return null;
    }

}