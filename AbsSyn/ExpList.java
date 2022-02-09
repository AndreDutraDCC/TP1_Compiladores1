package AbsSyn;

public class ExpList extends AbsNode{
    public Exp head;
    public ExpList tail;
    
    public ExpList(Exp cabeca, ExpList cauda){
        super();
        head = cabeca;
        tail = cauda;
        classname = "ExpList";
    }

    public void printkids(String pref){
        if(tail!=null){
            print(pref,head);
            tail.printkids(pref);
        }
        else{
            print(pref,head,true);
        }
    }
    
}
