package Intermediate;

import Intermediate.Nodes.*;

public class IntermediateTree {
    public IntermediateTree() {
        root_ = new SEQ(null, null);
        lastStmtNode_ = root_;
    }

    public void appendNode(Stm n) {
        if(freeStmtSlot_ < 2) {
            lastStmtNode_.children.set(freeStmtSlot_, n);
            freeStmtSlot_++;
        }else {
            SEQ newStmtNode = new SEQ(lastStmtNode_.children.get(0), n);
            lastStmtNode_.children.set(1, newStmtNode);
            lastStmtNode_ = newStmtNode;
        }
    }

    public String getTreeString() {
        return root_.stringRepresentation("");
    }

    private SEQ root_;
    private SEQ lastStmtNode_;
    private int freeStmtSlot_;
}
