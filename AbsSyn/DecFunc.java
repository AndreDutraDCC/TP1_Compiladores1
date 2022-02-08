package AbsSyn;

public class DecFunc extends Dec{
    public String      func_name;
    public String      return_type;
    public FieldTyList param_types;
    public Exp         body;

    public DecFunc(int p, String id, String type_id, FieldTyList params, Exp ex){
        pos = p;
        func_name = id;
        return_type = type_id;
        param_types = params;
        body = ex;
    }
}