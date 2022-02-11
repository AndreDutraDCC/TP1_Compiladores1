package Intermediate.Nodes;

import java.util.ArrayList;

public class MEM extends Stm {
    public MEM(Stm e) {
        name_ = "MEM";
        children = new ArrayList<Stm>(1);
        children.add(e);
    }
}
