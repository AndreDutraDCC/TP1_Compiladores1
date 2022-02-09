package AbsSyn;

public class VarIndexed extends Var{
    public Var var;
    public Exp index;

    public VarIndexed(int p, Var lvalue, Exp e){
        super();
        pos = p;
        var = lvalue;
        index = e;
        classname = "VarIndexed";
    }

    public void printkids(String pref){
        print(pref,var);
        print(pref,index,true);
    }
}
