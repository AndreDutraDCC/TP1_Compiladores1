package Intermediate.Nodes;

import java.util.ArrayList;

public class ESEQ extends Stm {
    public ESEQ(Stm statement, Stm expression) {
        name_ = "ESEQ";
        children = new ArrayList<Stm>(2);
        children.add(statement);
        children.add(expression);
    }
}
