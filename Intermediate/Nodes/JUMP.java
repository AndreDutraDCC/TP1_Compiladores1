package Intermediate.Nodes;

public class JUMP extends AbsNode {
    public JUMP(AbsNode expression, AbsNode[] labels) {
        name_ = "JUMP";
        children = new AbsNode[labels.length + 1];
        children[0] = expression;
        for(int i = 0; i < labels.length; i++)
            children[i + 1] = labels[i];
    }
}
