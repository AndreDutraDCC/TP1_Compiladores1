package AbsSyn;

public class ExpAttr extends Exp{
    public Var var;
    public Exp val;

    public ExpAttr(int p, Var vr, Exp vl){
        pos = p;
        var = vr;
        val = vl;
        classname = "ExpAttr";
    }

    public void printkids(String pref){
        print(pref,var);
        print(pref,val,true);
    }
    
}
