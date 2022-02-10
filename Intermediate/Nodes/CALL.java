package Intermediate.Nodes;

public class CALL extends Stm {
    public CALL(Stm function, Stm[] args) {
        name_ = "CALL";
        children = new Stm[args.length + 1];
        children[0] = function;
        for(int i = 0; i < args.length; i++)
            children[i + 1] = args[i];
    }
}
