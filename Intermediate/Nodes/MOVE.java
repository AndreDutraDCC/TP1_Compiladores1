package Intermediate.Nodes;

import java.util.ArrayList;

public class MOVE extends Stm {
    public MOVE(Stm destination, Stm source) {
        name_ = "MOVE";
        children = new ArrayList<Stm>(2);
        children.add(destination);
        children.add(source);
    }
}
