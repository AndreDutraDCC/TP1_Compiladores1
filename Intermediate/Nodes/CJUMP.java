package Intermediate.Nodes;

public class CJUMP extends AbsNode {
    public CJUMP(Operators o, AbsNode e1, AbsNode e2, AbsNode trueStmt, AbsNode falseStmt) {
        name_ = "CJUMP";
        children = new AbsNode[4];
        children[0] = null;
        children[1] = e1;
        children[2] = e2;
        children[3] = trueStmt;
        children[4] = falseStmt;
    }
}
