package AbsSyn;



public class DecList extends AbsNode{
    public Dec head;
    public DecList tail;
    
    public DecList(Dec cabeca, DecList cauda){
        super();
        head = cabeca;
        tail = cauda;
        classname = "DecList";
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