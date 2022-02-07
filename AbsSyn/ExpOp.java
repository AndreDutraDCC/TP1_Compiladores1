package AbsSyn;

public class ExpOp extends Exp{
    public int oper;
    public Exp e1,e2;

    public ExpOp(int p, Exp op1, int Op, Exp op2){
        pos = p;
        e1 = op1;
        oper = Op;
        e2 = op2;
    }
}
