package AbsSyn;

public class ExpLet extends Exp {
    public DecList decs;
    public ExpSeq exps;

    public ExpLet(int p, DecList dcs, ExpSeq exs){
        pos = p;
        decs = dcs;
        exps = exs;
    }
}
