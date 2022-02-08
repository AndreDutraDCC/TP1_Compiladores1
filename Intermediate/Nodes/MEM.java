package Intermediate.Nodes;

public class MEM extends AbsNode {
    public MEM(AbsNode e) {
        name_ = "MEM";
        children = new AbsNode[1];
        children[0] = e;
    }
}
