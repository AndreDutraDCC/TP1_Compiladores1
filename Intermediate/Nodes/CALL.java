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
        res += children.get(0).stringRepresentation(" " + prefix) + "\n";
        res += children.get(1).stringRepresentation("  " + prefix) + "\n";
        res += children.get(2).stringRepresentation("  " + prefix);
        return res;
    }
}
