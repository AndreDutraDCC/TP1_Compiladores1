package Intermediate.Nodes;

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
        /*switch(op_) {
            case PLUS1: return "PLUS";
            case MINUS1: return "MINUS";
            case MUL1: return "MUL";
            case DIV1: return "DIV";
            case AND1: return "AND";
            case OR1: return "OR";
            default: return "";
        }*/
        return "";
    }

    private int op_;
}
