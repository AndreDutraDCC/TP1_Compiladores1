package AbsSyn;

import java.util.ArrayList;

public class TyRec extends Ty{
    public FieldTyList field_types;
    public ArrayList<Integer> offsets;

    public TyRec(FieldTyList fields){
        super();
        field_types = fields;
        classname = "TyRec";
        offsets = null;
    }

    public void printkids(String pref){
        print(pref,field_types,true);
    }

    
}
