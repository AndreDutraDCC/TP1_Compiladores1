package Intermediate.Nodes;

public class BINOP extends AbsNode {
    public BINOP(Operators o, AbsNode e1, AbsNode e2) {
        name_ = "BINOP";
        children = new AbsNode[3];
        children[0] = this;
        children[1] = e1;
        children[2] = e2;
        op_ = o;
    }

    @Override
    public String toString() {
        return name_ + "(" + operatorName() + ",\n" + childrenToString() + ")";
    }

    private String operatorName() {
        switch(op_) {
            case PLUS: return "PLUS";
            case MINUS: return "MINUS";
            case MUL: return "MUL";
            case DIV: return "DIV";
            case AND: return "AND";
            case OR: return "OR";
            default: return "";
        }
    }

    private Operators op_;
}
