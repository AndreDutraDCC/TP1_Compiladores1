package AbsSyn;

public class VarSimple extends Var{
    public String name;

    public VarSimple(int p, String id){
        super();
        pos = p;
        name = id;
        classname = "VarSimple";
    }

    public void printkids(String pref){
        print(pref,name,true);
    }
}
