package Intermediate.Nodes;

public class SEQ extends AbsNode {
    public SEQ(AbsNode s1, AbsNode s2) {
        name_ = "SEQ";
        children = new AbsNode[2];
        children[0] = s1;
        children[1] = s2;
    }
}
