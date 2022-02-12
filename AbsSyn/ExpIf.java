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
        Boolean is_last = else_body == null;

        print(pref,cond);
        print(pref,then_body,is_last);
        print(pref,else_body,true);
    }
}
