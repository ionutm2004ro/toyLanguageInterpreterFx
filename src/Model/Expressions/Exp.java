package Model.Expressions;
import Exceptions.MyArithEvalException;
import Model.Containers.MyIDictionary;
import Model.Containers.MyIHeap;

public abstract class Exp {
    public abstract int eval(MyIDictionary<String,Integer>tbl,MyIHeap<Integer,Integer> heap) throws MyArithEvalException, Exception;
    public abstract String toString();
}
