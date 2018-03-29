package Controller;

import Model.Containers.PrgState;
import Model.Statements.IStmt;
import Repository.IRepository;
import Repository.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Exceptions.EmptyRepositoryException;
import javafx.scene.control.Alert;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller() {
        repo = new Repository();
    }

    public void addPrg(IStmt prg) {
        repo.addPrg(prg);
    }

    HashMap<Integer,Integer> conservativeGarbageCollector(Collection<Integer> symTableValues,
                                                          HashMap<Integer,Integer> heap){
        return (HashMap<Integer, Integer>) heap.entrySet().stream().
                filter(e->symTableValues.contains(e.getKey())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    void oneStepForAllPrg(List<PrgState> prgList) throws Exception {
        //before the execution, print the PrgState List into the log file
        prgList.forEach(prg ->repo.logPrgStateExec(prg));

        //RUN concurrently one step for each of the existing PrgStates
        //-----------------------------------------------------------------------
        //prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        List<PrgState> newPrgList =
                executor.invokeAll(callList).stream()
                        .map(future -> { try { return future.get();}
                        catch(Exception e) {Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Look, an error!");
                            alert.setHeaderText(e.getMessage());
                            alert.setContentText("The current erronous step will be skipped");

                            alert.showAndWait();
                        }
                            return null;
                        })
                        .filter(p -> p!=null)
                        .collect(Collectors.toList());

        //add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);
        //------------------------------------------------------------------------

        //after the execution, print the PrgState List into the log file
        prgList.forEach(prg ->repo.logPrgStateExec(prg));
        //Save the current programs in the repository
        repo.setPrgList(prgList);
    }

    public void allStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        List<PrgState> lastPrg = prgList;
        while(prgList.size() > 0){
            //do the garbage collector
            Collection<Integer> compositeSymTable = prgList.stream()
                    .map(prg->{return prg.getSymTable().getContent().values();})
                    .reduce((one,two)->Stream.concat(one.stream(),two.stream())
                            .collect(Collectors.toList())).get();
            prgList.get(0).getHeap().setContent(conservativeGarbageCollector(
                    compositeSymTable,
                    prgList.get(0).getHeap().getContent()));

            oneStepForAllPrg(prgList);
            //remove the completed programs
            lastPrg = prgList;
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository
        lastPrg.stream().forEach(f->f.getFileTable().getContent().entrySet().stream().forEach(e->{
            try {
                e.getValue().right().close();
            } catch (IOException e1) {
                System.out.println(e);
                e1.printStackTrace();
            }
        }));
        // update the repository state
        repo.setPrgList(prgList);
    }


    public List<PrgState> getPrgList(){
        return repo.getPrgList();
    }

    public boolean isExeStackEmpty(){
        for (PrgState prg : repo.getPrgList()) {
            if(!prg.getStk().isEmpty()){
                return false;
            }
        }
        return true;
    }

    public void oneStep() throws Exception{
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        List<PrgState> lastPrg = prgList;
        Collection<Integer> compositeSymTable = prgList.stream()
            .map(prg->{return prg.getSymTable().getContent().values();})
            .reduce((one,two)->Stream.concat(one.stream(),two.stream())
                    .collect(Collectors.toList())).get();
        prgList.get(0).getHeap().setContent(conservativeGarbageCollector(
                compositeSymTable,
                prgList.get(0).getHeap().getContent()));

        oneStepForAllPrg(prgList);
    }

    public String currentToString() throws EmptyRepositoryException{
        return repo.currentToString();
    }

	/*public PrgState getCrtPrg() throws EmptyRepositoryException {
		return repo.getCrtPrg();
	}*/
}
