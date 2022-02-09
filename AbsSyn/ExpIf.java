package AbsSyn;

public class ExpIf extends Exp{
    public Exp cond,then_body,else_body;

    public ExpIf(int p, Exp c, Exp t, Exp e){
        super();
        pos = p;
        cond = c;
        then_body = t;
        else_body = e;
        classname = "ExpIf";
    }

    public void printkids(String pref){
        print(pref,cond);
        print(pref,then_body);
        print(pref,else_body,true);
    }
}
