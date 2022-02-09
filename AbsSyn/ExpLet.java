package AbsSyn;

public class ExpLet extends Exp {
    public DecList decs;
    public ExpSeq exps;

    public ExpLet(int p, DecList dcs, ExpSeq exs){
        super();
        pos = p;
        decs = dcs;
        exps = exs;
        classname = "ExpLet";
    }

    public void printkids(String pref){
        print(pref,decs);
        print(pref,exps,true);
    }
}
