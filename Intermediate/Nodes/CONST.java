package Intermediate.Nodes;

public class CONST extends Stm {
    public CONST(int i) {
        name_ = "CONST";
        value_ = i;
    }

    @Override
    public String stringRepresentation(String prefix) {
        return prefix + "CONST " + value_;
    }

    private int value_;
}
