package Intermediate.Nodes;

public class TEMP extends Stm {
    public TEMP(int t) {
        name_ = "TEMP";
        id_ = t;
    }

    @Override
    public String stringRepresentation(String prefix) {
        if(id_ == -1)
            return prefix + "TEMP fp";
        else if(id_ == -2)
            return prefix + "TEMP rv";
        return prefix + "TEMP t" + id_;
    }

    int id_;
}
