package Intermediate.Nodes;

public class NAME extends Stm {
    public NAME(String name) {
        name_ = name;
    }

    @Override
    public String stringRepresentation(String prefix) {
        return prefix + "NAME " + name_;
    }

    private String name_;
}
