package AbsSyn;

public class FieldTyList extends AbsNode{
    public String field_id;
    public String field_type;
    public FieldTyList tail;

    public FieldTyList(String id, String type_id, FieldTyList cauda){
        field_id = id;
        field_type = type_id;
        tail = cauda;
        classname = "FieldTyList";
    }

    public void printkids(String pref){
        if(tail!=null){
            System.out.println(pref+"\u2523\u2533\""+field_id+"\"");
            System.out.println(pref+"\u2503\u2517\""+field_type+"\"");
            tail.printkids(pref);
        }
        else{
            System.out.println(pref+"\u2517\u2533\""+field_id+"\"");
            System.out.println(pref+" \u2517\""+field_type+"\"");
        }
    }
}
