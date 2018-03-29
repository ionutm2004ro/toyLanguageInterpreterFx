package Repository;

import java.util.List;

import Model.Containers.PrgState;
import Model.Statements.IStmt;

public interface IRepository {
    void addPrg(IStmt prg);
    String currentToString();
    void logPrgStateExec(PrgState prgState);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newPrgList);
}
