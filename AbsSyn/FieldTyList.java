package AbsSyn;

public class FieldTyList {
    public String field_id;
    public String field_type;
    public FieldTyList tail;

    public FieldTyList(String id, String type_id, FieldTyList cauda){
        field_id = id;
        field_type = type_id;
        tail = cauda;
    }
}
