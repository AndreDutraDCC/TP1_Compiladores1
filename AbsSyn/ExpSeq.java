package AbsSyn;

public class ExpSeq extends Exp{
    public ExpList exps;

    public ExpSeq(int p, ExpList exs){
        pos = p;
        exps = exs;
    }
}
