package AbsSyn;



public class ExpNil extends Exp {
    public ExpNil(int p){
        super();
        pos = p;
        classname = "Nil";
    }

    public void printkids(String pref){}
}
