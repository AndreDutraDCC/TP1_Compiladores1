package AbsSyn;

public class FieldExpList {
    public String field_id;
    public Exp field_value;
    public FieldExpList tail;

    public FieldExpList(String id, Exp ex, FieldExpList cauda){
        field_id = id;
        field_value = ex;
        tail = cauda;
    }
}
