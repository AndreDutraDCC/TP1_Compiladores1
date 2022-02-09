package AbsSyn;

public class ExpWhile extends Exp{
    public Exp cond,body;

    public ExpWhile(int p, Exp con, Exp bod){
        super();
        pos = p;
        cond = con;
        body = bod;
        classname = "ExpWhile";
    }

    public void printkids(String pref){
        print(pref,cond);
        print(pref,body,true);
    }
}
