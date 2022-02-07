package AbsSyn;

public class TyRec extends Ty{
    public FieldTyList field_types;

    public TyRec(FieldTyList fields){
        field_types = fields;
    }
}
