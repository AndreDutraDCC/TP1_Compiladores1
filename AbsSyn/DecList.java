package AbsSyn;

public class DecList extends AbsNode{
    public Dec head;
    public ExpList tail;
    
    public DecList(Dec cabeca, ExpList cauda){
        head = cabeca;
        tail = cauda;
    }
    
}