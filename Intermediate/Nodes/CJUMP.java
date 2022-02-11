package Intermediate.Nodes;

import java.util.ArrayList;

public class CJUMP extends Stm {
    public CJUMP(int o, Stm e1, Stm e2, Stm trueStmt, Stm falseStmt) {
        name_ = "CJUMP";
        children = new ArrayList<Stm>(4);
        children.add(null);
        children.add(e1);
        children.add(e2);
        children.add(trueStmt);
        children.add(falseStmt);
    }
}
