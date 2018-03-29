package Model.Statements;

import Exceptions.MyArithEvalException;
import Model.Containers.PrgState;

public class returnStmt implements IStmt {

    public returnStmt(){
    }

    @Override
    public PrgState execute(PrgState state) throws MyArithEvalException, Exception {
        state.popSymTable();
        return null;
    }

    @Override
    public String toString(){
        return "return";
    }
}
