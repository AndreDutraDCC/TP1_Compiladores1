package AbsSyn;

public class ExpString extends Exp {
    public String val;

    public ExpString(int p, String v){
        pos = p;
        val = v.substring(1,v.length()-1);
        classname = "ExpString";
    }

    public void printkids(String pref){
        print(pref,val,true);
    }
    
}
