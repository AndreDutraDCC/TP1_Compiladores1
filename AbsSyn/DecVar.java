package AbsSyn;

public class DecVar extends Dec{
    public String var_name;
    public String var_type;
    public Exp    var_value;

    public DecVar(int p, String id, String type, Exp val){
        pos = p;
        var_name = id;
        var_type = type;
        var_value = val;
        classname = "DecVar";
    }

    public void printkids(String pref){
        print(pref,var_name);
        print(pref,var_type);
        print(pref,var_value,true);
    }
}
