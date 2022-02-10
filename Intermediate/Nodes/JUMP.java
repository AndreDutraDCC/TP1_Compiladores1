package Intermediate.Nodes;

public class JUMP extends Stm {
    public JUMP(Stm expression, Stm[] labels) {
        name_ = "JUMP";
        children = new Stm[labels.length + 1];
        children[0] = expression;
        for(int i = 0; i < labels.length; i++)
            children[i + 1] = labels[i];
    }
}
