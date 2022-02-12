package Intermediate.Nodes;

import java.util.ArrayList;

public class JUMP extends Stm {
    public JUMP(Stm label) {
        name_ = "JUMP";
        children = new ArrayList<Stm>(1);
        children.add(label);
    }
}
