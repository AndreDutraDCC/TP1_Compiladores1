package AbsSyn;

public class ExpWhile extends Exp{
    public Exp cond,body;

    public ExpWhile(int p, Exp con, Exp bod){
        pos = p;
        cond = con;
        body = bod;
    }
}
