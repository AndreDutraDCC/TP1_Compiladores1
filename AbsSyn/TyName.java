package AbsSyn;

public class TyName extends Ty{
    public String ty_name;

    public TyName(String type_id){
        super();
        ty_name = type_id;
        classname = "TyName";
    }

    public void printkids(String pref){
        print(pref,ty_name,true);
    }
}
