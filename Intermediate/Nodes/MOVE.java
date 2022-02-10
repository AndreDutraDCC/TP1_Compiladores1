package Intermediate.Nodes;

public class MOVE extends Stm {
    public MOVE(Stm destination, Stm source) {
        name_ = "MOVE";
        children = new Stm[2];
        children[0] = destination;
        children[1] = source;
    }
}
