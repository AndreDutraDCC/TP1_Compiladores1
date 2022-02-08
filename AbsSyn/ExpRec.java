package AbsSyn;

public class ExpRec extends Exp {
    public String rec_type;
    public FieldExpList fields;

    public ExpRec(int p, String type, FieldExpList list){
        pos = p;
        rec_type = type;
        fields = list;
        classname = "ExpRec";
    }

    public void printkids(String pref){
        print(pref,rec_type);
        print(pref,fields,true);
    }
}
