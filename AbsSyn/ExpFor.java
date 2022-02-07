package AbsSyn;

public class ExpFor extends Exp{
    public DecVar itr_var;
    public Exp    itr_end,body;

    public ExpFor(int p, DecVar d, Exp end, Exp bod){
        pos = p;
        itr_var = d;
        itr_end = end;
        body = bod;
    }

}
