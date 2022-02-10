package Intermediate.Nodes;

public class ESEQ extends Stm {
    ESEQ(Stm statement, Stm expression) {
        name_ = "ESEQ";
        children = new Stm[2];
        children[0] = statement;
        children[1] = expression;
    }
}
