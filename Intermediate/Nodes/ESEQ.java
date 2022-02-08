package Intermediate.Nodes;

public class ESEQ extends AbsNode {
    ESEQ(AbsNode statement, AbsNode expression) {
        name_ = "ESEQ";
        children = new AbsNode[2];
        children[0] = statement;
        children[1] = expression;
    }
}
