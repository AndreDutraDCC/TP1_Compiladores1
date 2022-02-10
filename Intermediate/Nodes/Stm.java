package Intermediate.Nodes;

public abstract class Stm {
    
    @Override
    public java.lang.String toString() {
        String res = name_ + "(\n";
        res += childrenToString();
        return res + ")";
    }

    protected String childrenToString() {
        String res = "";
        for(int i = 0; i < children.length - 1; i++)
            res += children[i] + ",\n";
        res += children[children.length - 1];
        return res;
    }

    String name_;
    public Stm[] children;
}
