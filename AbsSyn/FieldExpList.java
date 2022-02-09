package AbsSyn;

public class FieldExpList extends AbsNode{
    public String field_id;
    public Exp field_value;
    public FieldExpList tail;

    public FieldExpList(String id, Exp ex, FieldExpList cauda){
        super();
        field_id = id;
        field_value = ex;
        tail = cauda;
        classname = "FieldExpList";
    }

    public void printkids(String pref){
        if(tail!=null){
            System.out.println(pref+"\u2523\u2533\""+field_id+"\"");
            if(field_value!=null){
                System.out.println(pref+"\u2503\u2517"+classname);
                field_value.printkids(pref+"\u2503 ");
            }
            tail.printkids(pref);
        }
        else{
            System.out.println(pref+"\u2517\u2533\""+field_id+"\"");
            if(field_value!=null){
                System.out.println(pref+" \u2517"+classname);
                field_value.printkids(pref+"  ");
            }
        }
    }
}
