package Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Model.Containers.*;
import Model.Expressions.ArithExp;
import Model.Expressions.ConstExp;
import Model.Expressions.Exp;
import Model.Expressions.VarExp;
import Model.Statements.AssignStmt;
import Model.Statements.CompStmt;
import Model.Statements.IStmt;
import Model.Statements.PrintStmt;

public class Repository implements IRepository {
    private List<PrgState> prgList;
    private int[] idCounterReference={1};

    public Repository() {
        prgList = new ArrayList<PrgState>();
    }

    public void addPrg(IStmt prg) {
        Stack<MyIDictionary<String,Integer>> newStk=new Stack<MyIDictionary<String,Integer>>();
        newStk.push(new MyDictionary<String,Integer>());
        prgList.add(new PrgState(
                new MyStack<IStmt>(),
                newStk,
                new MyList<Integer>(),
                prg,
                new MyFileTable(),
                new MyHeap<Integer,Integer>(),
                idCounterReference[0]++,
                new ProcTable<String,Pair<List<String>, IStmt>>(),
                idCounterReference
        ));
        //can add procedures here procedure sum(a,b) v=a+b;print(v)
        List<String> tmpVarList = new ArrayList<String>();
        tmpVarList.add("a");
        tmpVarList.add("b");
        try {
            prgList.get(0).addProc("sum", tmpVarList, new CompStmt(new AssignStmt("v",new ArithExp('+',new VarExp("a"),new VarExp("b"))),new PrintStmt(new VarExp("v"))));

            prgList.get(0).addProc("product", tmpVarList, new CompStmt(new AssignStmt("v",new ArithExp('*',new VarExp("a"),new VarExp("b"))),new PrintStmt(new VarExp("v"))));
        }
        catch (Exception e){}
    }

    public String currentToString() {
        return prgList.toString();
    }

	/*@Override
	public PrgState getCrtPrg() throws EmptyRepositoryException {
		if (current==-1) {
			throw new EmptyRepositoryException("There is no program in the repo");
		}
		return prgList.get(current);
	}

	public String currentToString() throws EmptyRepositoryException {
		if (current==-1) {
			throw new EmptyRepositoryException("There is no program in the repo");
		}
		return prgList.get(current).toString();
	}
	*/

    public List<PrgState> getPrgList(){
        return prgList;
    }

    public void setPrgList(List<PrgState> newPrgList) {
        prgList=newPrgList;
    }

    public void logPrgStateExec(PrgState prgState) {
        String logfilepath = "log.txt";
        PrintWriter logFile = null;
        try {
            logFile= new PrintWriter(new BufferedWriter(new FileWriter(logfilepath,true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logFile.print(prgState.toString());
        logFile.close();
    }

}