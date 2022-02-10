package Intermediate.Nodes;

public class SEQ extends Stm {
    public SEQ(Stm s1, Stm s2) {
        name_ = "SEQ";
        children = new Stm[2];
        children[0] = s1;
        children[1] = s2;
    }
}
