package Intermediate.Nodes;

public class EXP extends AbsNode {
    public EXP(AbsNode e) {
        name_ = "EXP";
        children = new AbsNode[1];
        children[0] = e;
    }
}
