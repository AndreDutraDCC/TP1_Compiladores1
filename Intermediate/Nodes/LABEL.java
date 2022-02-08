package Intermediate.Nodes;

public class LABEL extends AbsNode {
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
