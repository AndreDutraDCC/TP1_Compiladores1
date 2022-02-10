package Intermediate.Nodes;

public class LABEL extends Stm {
    public LABEL(String name) {
        name_ = name;
        children = null;
    }

    @Override
    public String toString() {
        return "LABEL " + name_; 
    }

    private String name_;
}
