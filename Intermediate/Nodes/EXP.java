package Intermediate.Nodes;

public class EXP extends Stm {
    public EXP(Stm e) {
        name_ = "EXP";
        children = new Stm[1];
        children[0] = e;
    }
}
