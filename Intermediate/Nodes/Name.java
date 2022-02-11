package Intermediate.Nodes;

public class Name extends Stm {
    public Name(String name) {
        name_ = name;
    }

    @Override
    public String stringRepresentation(String prefix) {
        return prefix + "NAME " + name_;
    }

    private String name_;
}
