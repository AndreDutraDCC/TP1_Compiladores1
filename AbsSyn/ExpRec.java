package AbsSyn;

public class ExpRec extends Exp {
    public String rec_type;
    public FieldExpList fields;

    public ExpRec(int p, String type, FieldExpList list){
        pos = p;
        rec_type = type;
        fields = list;
    }
}
