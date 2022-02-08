package Intermediate.Nodes;

public class CONST extends AbsNode {
    public CONST(int i) {
        name_ = "CONST";
        value_ = i;
    }

    @Override
    public String toString() {
        return "CONST " + value_;
    }

    private int value_;
}
