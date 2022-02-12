package Intermediate.Nodes;

import java.util.ArrayList;

public class CALL extends Stm {
    public CALL(Stm function, Stm[] args) {
        name_ = "CALL";
        children = new ArrayList<Stm>();
        children.add(function);
        for(int i = 0; i < args.length; i++)
            children.add(args[i]);
    }

    @Override
    protected String childrenToString(String prefix) {
        String res = "";
        for(int i = 0; i < children.size() - 1; i++)
            res += children.get(i).stringRepresentation(" " + prefix) + "\n";
        res += children.get(children.size() - 1).stringRepresentation("  " + prefix);
        return res;
    }
}
