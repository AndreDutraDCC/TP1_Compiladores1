package Intermediate.Nodes;

import static Grammar.sym.*;

import java.util.ArrayList;

public class BINOP extends Stm {
    public BINOP(int o, Stm e1, Stm e2) {
        name_ = "BINOP";
        children = new ArrayList<Stm>(3);
        children.add(this);
        children.add(e1);
        children.add(e2);
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
