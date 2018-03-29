package Model.Statements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import Exceptions.MyArithEvalException;
import Model.Containers.MyFileTable;
import Model.Containers.MyIDictionary;
import Model.Containers.Pair;
import Model.Containers.PrgState;

public class openRFile implements IStmt {
    String var_file_id;
    String filename;

    public openRFile(String id,String name) {
        var_file_id=id;
        filename=name;
    }

    @Override
    public String toString() {
        return "openRFile("+var_file_id+","+filename+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyArithEvalException {
        state.duplicateStackTop();
        MyFileTable fileTbl = state.getFileTable();
        MyIDictionary<String,Integer> symTbl= state.getSymTable();
        // is filename already in filetable?
        fileTbl.hasValue(filename);
        // open file
        try {
            BufferedReader bufRead = new BufferedReader(new FileReader(filename));
            //new filetable entrance
            Integer fid = state.getFCount();
            fileTbl.add(fid,new Pair<String,BufferedReader>(filename,bufRead));
            //set the var_file_id to that new unique integer key
            if (symTbl.isDefined(var_file_id))
                symTbl.update(var_file_id,fid);
            else symTbl.add(var_file_id,fid);

        } catch (FileNotFoundException e) {
            System.out.println(filename+" COULD NOT BE FOUND!!11!");
            e.printStackTrace();
        }
        return null;
    }

}
