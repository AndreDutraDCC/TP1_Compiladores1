package AbsSyn;

public class ExpFor extends Exp{
    public DecVar itr_var;
    public Exp    itr_end,body;

    public ExpFor(int p, DecVar d, Exp end, Exp bod){
        pos = p;
        itr_var = d;
        itr_end = end;
        body = bod;
        classname = "ExpFor";
    }

    public void printkids(String pref){
        print(pref,itr_var);
        print(pref,itr_end);
        print(pref,body,true);
    }

}
