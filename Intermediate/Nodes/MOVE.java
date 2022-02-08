package Intermediate.Nodes;

public class MOVE extends AbsNode {
    public MOVE(AbsNode destination, AbsNode source) {
        name_ = "MOVE";
        children = new AbsNode[2];
        children[0] = destination;
        children[1] = source;
    }
}
