package AbsSyn;

import java.util.ArrayList;

import SymbolTable.Symbols.VarSymbol;

public class TyRec extends Ty{
    public FieldTyList field_types;
    public ArrayList<VarSymbol> fields;

    public TyRec(FieldTyList fields){
        super();
        field_types = fields;
        classname = "TyRec";
        fields = null;
    }

    public void printkids(String pref){
        print(pref,field_types,true);
    }

    
}
