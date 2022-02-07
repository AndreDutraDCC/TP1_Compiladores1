package AbsSyn;

public class DecType extends Dec{
    public String type_name;
    public Ty     type_body;

    public DecType(int p, String id, Ty type){
        pos = p;
        type_name = id;
        type_body = type;
    }
}
