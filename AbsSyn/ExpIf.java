package AbsSyn;

public class ExpIf extends Exp{
    public Exp cond,then_body,else_body;

    public ExpIf(int p, Exp c, Exp t, Exp e){
        pos = p;
        cond = c;
        then_body = t;
        else_body = e;
    }
}
