package Intermediate.Nodes;

public class LABEL extends Stm {
    public LABEL(String name) {
        name_ = name;
        children = null;
    }

    @Override
    public String stringRepresentation(String prefix) {
        return prefix + "LABEL " + name_; 
    }

    private String name_;
}
