package AbsSyn;

public class ExpCall extends Exp {
    String func_name;
    ExpList args;

    public ExpCall(int p, String id, ExpList ars){
        pos = p;
        func_name = id;
        args = ars;
    }
}
