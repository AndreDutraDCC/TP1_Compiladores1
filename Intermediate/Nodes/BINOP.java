package Intermediate.Nodes;

import static Grammar.sym.*;

public class BINOP extends Stm {
    public BINOP(int o, Stm e1, Stm e2) {
        name_ = "BINOP";
        children = new Stm[3];
        children[0] = this;
        children[1] = e1;
        children[2] = e2;
        op_ = o;
    }

    @Override
    public String stringRepresentation(String prefix) {
        return prefix + name_ + "(" + operatorName() + ",\n" + childrenToString(" " + prefix) + ")";
    }

    private String operatorName() {
        return terminalNames[op_];
    }

    private int op_;
}
