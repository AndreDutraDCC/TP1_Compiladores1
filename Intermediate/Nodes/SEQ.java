package Intermediate.Nodes;

import java.util.ArrayList;

public class SEQ extends Stm {
    public SEQ(Stm s1, Stm s2) {
        name_ = "SEQ";
        children = new ArrayList<Stm>(2);
        children.add(s1);
        children.add(s2);
    }
}
