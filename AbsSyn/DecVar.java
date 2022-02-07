package AbsSyn;

public class DecVar extends Dec{
    public String var_name;
    public TyName var_type;
    public Exp    var_value;

    public DecVar(int p, String id, TyName type, Exp val){
        pos = p;
        var_name = id;
        var_type = type;
        var_value = val;
    }
}
