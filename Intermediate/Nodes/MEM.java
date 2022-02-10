package Intermediate.Nodes;

public class MEM extends Stm {
    public MEM(Stm e) {
        name_ = "MEM";
        children = new Stm[1];
        children[0] = e;
    }
}
