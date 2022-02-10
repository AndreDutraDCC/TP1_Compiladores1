package Intermediate.Nodes;

public class CJUMP extends Stm {
    public CJUMP(int o, Stm e1, Stm e2, Stm trueStmt, Stm falseStmt) {
        name_ = "CJUMP";
        children = new Stm[4];
        children[0] = null;
        children[1] = e1;
        children[2] = e2;
        children[3] = trueStmt;
        children[4] = falseStmt;
    }
}
