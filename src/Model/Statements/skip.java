package Model.Statements;

import Exceptions.MyArithEvalException;
import Model.Containers.PrgState;

public class skip implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws MyArithEvalException, Exception {
        return null;
    }

    @Override
    public String toString() {
        return "skip";
    }
}