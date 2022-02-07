package AbsSyn;

public class VarIndexed extends Var{
    public Var var;
    public Exp index;

    public VarIndexed(int p, Var lvalue, Exp e){
        pos = p;
        var = lvalue;
        index = e;
    }
}
