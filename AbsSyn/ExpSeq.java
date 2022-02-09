package AbsSyn;

public class ExpSeq extends Exp{
    public ExpList exps;

    public ExpSeq(int p, ExpList exs){
        super();
        pos = p;
        exps = exs;
        classname = "ExpSeq";
    }

    public void printkids(String pref){
        print(pref,exps,true);
    }
}
