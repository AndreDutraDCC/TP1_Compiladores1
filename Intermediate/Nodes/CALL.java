package Intermediate.Nodes;

import java.util.ArrayList;

public class CALL extends Stm {
    public CALL(Stm function, ArrayList<Stm> args) {
        name_ = "CALL";
        children.add(function);
        for(Stm arg: args){
            children.add(arg);
        }
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
