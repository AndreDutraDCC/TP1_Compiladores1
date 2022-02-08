package AbsSyn;

public class ExpInt extends Exp{
    public int val;

    public ExpInt(int p, int v){
        pos = p;
        val = v;
        classname = "ExpInt";
    }

    public void printkids(String pref){
        print(pref,val);
    }
    
}
