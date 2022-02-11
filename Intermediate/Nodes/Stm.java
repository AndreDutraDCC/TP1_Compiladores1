package Intermediate.Nodes;

import java.util.ArrayList;

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
        for(int i = 0; i < children.size() - 1; i++)
            if(children.get(i) != null)
                res += children.get(i).stringRepresentation(prefix + " ") + ",\n";
        if(children.get(children.size() - 1 ) != null)
            res += children.get(children.size() - 1).stringRepresentation(prefix + " ");
        return res;
    }

    String name_;
    public ArrayList<Stm> children;
}
