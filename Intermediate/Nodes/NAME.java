package Intermediate.Nodes;

public class NAME extends Stm {
    public NAME(String name) {
        name_ = name;
    }

    @Override
    public String toString() {
        return "NAME " + name_;
    }

    private String name_;
}
