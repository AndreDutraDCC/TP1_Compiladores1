package AbsSyn;



public class ExpArray extends Exp {
    String arr_type;
    Exp len;
    Exp val;

    public ExpArray(int p, String typ, Exp l, Exp v){
        super();
        pos = p;
        arr_type = typ;
        len = l;
        val = v;
        classname = "ExpArray";
    }

    public void printkids(String pref){
        print(pref,arr_type);
        print(pref,len);
        print(pref,val,true);
    }
}
