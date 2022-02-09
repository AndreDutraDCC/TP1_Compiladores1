package AbsSyn;

public class TyRec extends Ty{
    public FieldTyList field_types;

    public TyRec(FieldTyList fields){
        super();
        field_types = fields;
        classname = "TyRec";
    }

    public void printkids(String pref){
        print(pref,field_types,true);
    }
}
