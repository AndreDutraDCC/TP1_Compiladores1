package AbsSyn;

public class VarField extends Var{
    public Var    var;
    public String field_name;

    public VarField(int p,Var v, String id){
        pos = p;
        var = v;
        field_name = id;
    }
}