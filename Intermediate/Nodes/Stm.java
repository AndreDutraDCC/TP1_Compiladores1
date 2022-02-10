package Intermediate.Nodes;

public abstract class Stm {
    
    public String stringRepresentation(String prefix) {
        String res = prefix + name_ + "(\n";
        res += childrenToString(prefix);
        return res + ")";
    }

    protected String childrenToString(String prefix) {
        String res = "";
        if(children == null)
            return res;
        for(int i = 0; i < children.length - 1; i++)
            res += children[i].stringRepresentation(prefix + " ") + ",\n";
        res += children[children.length - 1].stringRepresentation(prefix + " ");
        return res;
    }

    String name_;
    public Stm[] children;
}
