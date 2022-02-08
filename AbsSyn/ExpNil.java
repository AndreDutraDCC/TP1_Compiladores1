package AbsSyn;

public class ExpNil extends Exp {
    public ExpNil(int p){
        pos = p;
        classname = "Nil";
    }

    public void printkids(String pref){}
}
