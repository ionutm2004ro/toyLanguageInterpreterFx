package Model.Containers;

import Exceptions.MyStmtExecException;
import Model.Expressions.Exp;
import Model.Expressions.VarExp;
import Model.Statements.IStmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class PrgState {
    MyIStack<IStmt> exeStack;
    Stack<MyIDictionary<String, Integer>> symTable;
    MyIList<Integer> out;
    IStmt originalProgram;//optional field

    MyFileTable FileTable;
    Integer filecount=0;
    Integer id;
    int[] idCountReference;

    MyIHeap<Integer,Integer> heap;

    IProcTable<String,Pair<List<String>,IStmt>> procTbl;

    public PrgState(MyIStack<IStmt> stk, Stack<MyIDictionary<String, Integer>> symtbl,MyIList<Integer> ot, IStmt prg,
                    MyFileTable fTbl,MyIHeap<Integer,Integer> h,Integer id,IProcTable<String,Pair<List<String>,
                    IStmt>> procTable, int[] ref){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        originalProgram = prg;
        stk.push(prg);
        FileTable = fTbl;
        heap = h;
        this.id = id;
        procTbl = procTable;
        idCountReference = ref;
    }

    public void setId(Integer newId) {
        id=newId;
    }

    public Integer getId() {
        return id;
    }

    public MyIStack<IStmt> getStk(){
        return exeStack;
    }

    public void setStk(MyIStack<IStmt> newStack) {
        exeStack = newStack;
    }

    public MyIDictionary<String, Integer> getSymTable() {
        MyIDictionary<String,Integer> topSymTable = symTable.pop();
        symTable.push(topSymTable);
        return topSymTable;
    }
    public Stack<MyIDictionary<String, Integer>> getSymTableClone() {
        return (Stack<MyIDictionary<String, Integer>>)symTable.clone();
    }

    public void setSymTable(MyIDictionary<String, Integer> newTable) {
        symTable.push(newTable);
    }

    public MyIList<Integer> getList(){
        return out;
    }

    public void setList(MyIList<Integer> newList) {
        out = newList;
    }

    public IStmt getOriginal() {
        return originalProgram;
    }
    public MyFileTable getFileTable(){
        return FileTable;
    }

    public Integer getFCount() {
        return filecount++;
    }

    public MyIHeap<Integer,Integer> getHeap(){
        return heap;
    }

    public MyIList<Integer> getOut(){
        return out;
    }

    public boolean isNotCompleted() {
        if (exeStack.isEmpty()) {
            return false;
        }
        return true;
    }

    public PrgState oneStep() throws Exception{
        if(exeStack.isEmpty()) throw new MyStmtExecException("empty stack");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public IProcTable<String,Pair<List<String>,IStmt>> getProcTbl(){
        return procTbl;
    }

    public void addProc(String name,List<String> varList,IStmt statement) throws Exception{
        procTbl.add(name,new Pair(varList,statement));
    }

    public void duplicateStackTop(){
        MyIDictionary<String, Integer> tmPop = symTable.pop();
        symTable.push(tmPop);
        symTable.push(new MyDictionary<String,Integer>((HashMap<String, Integer>) tmPop.getContent().clone()));
    }

    public void popSymTable(){
        symTable.pop();
    }

    public int[] getIdCountReference(){
        return idCountReference;
    }

    @Override
    public String toString() {
        return //"Original Program: "+originalProgram.toString()+"\n"+
                "tid:"+id.toString()+"--------------------------------------------------------\n"+
                        "Exe Stack:\n"+exeStack.toString()+"\n"+
                        "SymTable:\n"+symTable.toString()+"\n"+
                        "Out:\n"+out.toString()+"\n"+
                        "FileTable:\n"+FileTable.toString()+
                        "Heap:\n"+heap.toString()+
                        "ProcTable:\n"+procTbl.toString()+
                        "-------------------------------------------------------------\n";
    }
}