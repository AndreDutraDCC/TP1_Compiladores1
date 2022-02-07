package AbsSyn;

public class ExpArray extends Exp {
    String arr_type;
    Exp len;
    Exp val;

    public ExpArray(int p, String type, Exp l, Exp v){
        pos = p;
        arr_type = type;
        len = l;
        val = v;
    }
}
