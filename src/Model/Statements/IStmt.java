package Model.Statements;

import Exceptions.MyArithEvalException;
import Model.Containers.PrgState;

public interface IStmt {
    String toString();
    PrgState execute(PrgState state) throws MyArithEvalException, Exception;
}

