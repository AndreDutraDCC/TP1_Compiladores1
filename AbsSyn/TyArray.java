package AbsSyn;

public class TyArray extends Ty{
    public String elem_type;

    public TyArray(String type_id){
        elem_type = type_id;
        classname = "TyArray";
    }

    public void printkids(String pref){
        print(pref,elem_type,true);
    }
}

