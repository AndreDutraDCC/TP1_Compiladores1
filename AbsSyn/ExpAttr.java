package AbsSyn;

public class ExpAttr extends Exp{
    public Var var;
    public Exp val;

    public ExpAttr(int p, Var vr, Exp vl){
        pos = p;
        var = vr;
        val = vl;
    }
    
}
