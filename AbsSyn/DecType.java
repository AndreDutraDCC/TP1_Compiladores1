package AbsSyn;



public class DecType extends Dec{
    public String type_name;
    public Ty     type_body;

    public DecType(int p, String id, Ty typ){
        super();
        pos = p;
        type_name = id;
        type_body = typ;
        classname = "DecType";
    }

    public void printkids(String pref){
        print(pref,type_name);
        print(pref,type_body,true);
    }
}
