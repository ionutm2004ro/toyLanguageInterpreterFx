package View.App;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import Controller.Controller;
import Model.Expressions.ArithExp;
import Model.Expressions.BoolExp;
import Model.Expressions.ConstExp;
import Model.Expressions.VarExp;
import Model.Expressions.rHExp;
import Model.Statements.*;
import View.ExitCommand;
import View.RunExample;
import View.TextMenu;

public class Interpreter {
    public static void main(String[] args) {
        PrintWriter writer;
        try {
            writer = new PrintWriter("log.txt");
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        IStmt ex0 = new PrintStmt(new BoolExp(new ConstExp(1),"<",new ConstExp(2)));
        Controller ctr0 = new Controller();
        ctr0.addPrg(ex0);

        IStmt ex1 = new CompStmt(
                new AssignStmt(
                        "v",
                        new ConstExp(2)),
                new PrintStmt(new VarExp("v")));


        IStmt ex2 = new CompStmt(
                new AssignStmt(
                        "a",
                        new ArithExp(
                                '+',
                                new ConstExp(2),
                                new ArithExp(
                                        '*',
                                        new ConstExp(3),
                                        new ConstExp(5)))),
                new CompStmt(
                        new AssignStmt(
                                "b",
                                new ArithExp(
                                        '+',
                                        new VarExp("a"),
                                        new ConstExp(1))),
                        new PrintStmt(new VarExp("b"))));

        IStmt ex4 = new CompStmt(
                new openRFile("f","test.in"),
                new CompStmt(
                        new readFile(new VarExp("f"),"c"),
                        new CompStmt(
                                new PrintStmt(new VarExp("c")),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("c"),
                                                new CompStmt(
                                                        new readFile(new VarExp("f"),"c"),
                                                        new PrintStmt(new VarExp("c"))
                                                ),
                                                new PrintStmt(new ConstExp(0))
                                        ),
                                        new CompStmt(
                                            new closeRFile(new VarExp("f")),
                                            new PrintStmt(new ConstExp(123123))
                                        )
                                )
                        )
                )
        );

        IStmt ex6 = new CompStmt(
                new AssignStmt("v", new ConstExp(10)),
                new CompStmt(
                        new newStmt("v", new ConstExp(20)),
                        new CompStmt(
                                new newStmt("a", new ConstExp(22)),
                                new CompStmt(
                                        new wHStmt("a", new ConstExp(30)),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("a")),
                                                new CompStmt(
                                                        new PrintStmt(new rHExp("a")),
                                                        new CompStmt(
                                                                new AssignStmt("a", new ConstExp(0)),
                                                                new skip()
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        IStmt ex7 = new CompStmt(
                new AssignStmt("v", new ConstExp(6)),
                new CompStmt(
                        new WhileStmt(
                                //new ArithExp('-', new VarExp("v"), new ConstExp(4)),
                                new BoolExp(new VarExp("v"), "!=", new ConstExp(4)),
                                new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-',new VarExp("v"), new ConstExp(1))))
                        ),
                        new PrintStmt(new VarExp("v"))
                )
        );

        IStmt ex8 = new CompStmt(
                new AssignStmt("v",new ConstExp(10)),
                new CompStmt(
                        new newStmt("a",new ConstExp(22)),
                        new CompStmt(
                                new ForkStmt(new CompStmt(
                                        new wHStmt("a",new ConstExp(30)),
                                        new CompStmt(
                                                new AssignStmt("v",new ConstExp(32)),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new rHExp("a"))
                                                )
                                        )
                                )),
                                new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new PrintStmt(new rHExp("a"))
                                )
                        )
                )
        );

        Controller ctr1 = new Controller();
        Controller ctr2 = new Controller();
        Controller ctr4 = new Controller();
        Controller ctr6 = new Controller();
        Controller ctr7 = new Controller();
        Controller ctr8 = new Controller();

        ctr1.addPrg(ex1);
        ctr2.addPrg(ex2);
        ctr4.addPrg(ex4);
        ctr6.addPrg(ex6);
        ctr7.addPrg(ex7);
        ctr8.addPrg(ex8);

        StmtArray ex9 = new StmtArray();
        ex9.add(new AssignStmt("v", new ConstExp(3)));
        ex9.add(new PrintStmt(new VarExp("v")));

        Controller ctr9 = new Controller();
        ctr9.addPrg(ex9);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),ctr1));
        menu.addCommand(new RunExample("2",ex2.toString(),ctr2));
        menu.addCommand(new RunExample("4",ex4.toString(),ctr4));
        menu.addCommand(new RunExample("6",ex6.toString(),ctr6));
        menu.addCommand(new RunExample("7",ex7.toString(),ctr7));
        menu.addCommand(new RunExample("8",ex8.toString(),ctr8));
        menu.addCommand(new RunExample("9",ex9.toString(),ctr9));
        menu.addCommand(new RunExample("10",ex0.toString(),ctr0));
        menu.show();
    }
}
