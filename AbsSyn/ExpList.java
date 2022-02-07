package AbsSyn;

public class ExpList extends AbsNode{
    public Exp head;
    public ExpList tail;
    
    public ExpList(Exp cabeca, ExpList cauda){
        head = cabeca;
        tail = cauda;
    }
    
}
