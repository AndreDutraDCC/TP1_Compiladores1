package AbsSyn;

public class ExpCall extends Exp {
    public String func_name;
    public ExpList args;

    public ExpCall(int p, String id, ExpList ars){
        super();
        pos = p;
        func_name = id;
        args = ars;
        classname = "ExpCall";
    }

    public void printkids(String pref){
        print(pref,func_name);
        print(pref,args,true);
    }
}
