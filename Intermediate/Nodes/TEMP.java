package Intermediate.Nodes;

public class TEMP extends AbsNode {
    public TEMP(int t) {
        name_ = "TEMP";
        id_ = t;
    }

    @Override
    public String stringRepresentation(String prefix) {
        return prefix + "TEMP t" + id_;
    }

    int id_;
}
