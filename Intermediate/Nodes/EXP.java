package Intermediate.Nodes;

import java.util.ArrayList;

public class EXP extends Stm {
    public EXP(Stm e) {
        name_ = "EXP";
        children = new ArrayList<Stm>(1);
        children.add(e);
    }
}
