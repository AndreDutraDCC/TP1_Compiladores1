package Intermediate.Nodes;

public class CALL extends AbsNode {
    public CALL(AbsNode function, AbsNode[] args) {
        name_ = "CALL";
        children = new AbsNode[args.length + 1];
        children[0] = function;
        for(int i = 0; i < args.length; i++)
            children[i + 1] = args[i];
    }
}
