package AbsSyn;

import Grammar.sym;


public class ExpOp extends Exp{
    public int oper;
    public Exp e1,e2;

    public ExpOp(int p, Exp op1, int Op, Exp op2){
        super();
        pos = p;
        e1 = op1;
        oper = Op;
        e2 = op2;
        classname = "ExpOp";
    }

    public void printkids(String pref){
        print(pref,e1);
        print(pref,sym.terminalNames[oper]);
        print(pref,e2,true);
    }
}
