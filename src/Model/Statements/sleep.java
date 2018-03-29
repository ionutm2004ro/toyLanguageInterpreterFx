package Model.Statements;

import Model.Containers.MyIDictionary;
import Model.Containers.MyIStack;
import Model.Containers.PrgState;
import Model.Expressions.Exp;

public class sleep implements IStmt{
    Integer num;

    public sleep(int number) {
        num=number;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIStack<IStmt> stack = state.getStk();
        if (num > 0) {
            stack.push(new sleep(num-1));
        }
        return null;
    }

    @Override
    public String toString() {
        return "sleep(" + num.toString() +")";
    }

}