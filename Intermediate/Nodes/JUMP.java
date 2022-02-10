package Intermediate.Nodes;

import java.util.ArrayList;

public class JUMP extends Stm {
    public JUMP(Stm expression, Stm[] labels) {
        name_ = "JUMP";
        children = new ArrayList<Stm>(labels.length + 1);
        children.add(expression);
        for(int i = 0; i < labels.length; i++)
            children.add(labels[i]);
    }
}
