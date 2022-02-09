package Types;

public class VOID extends Type {
    public VOID() {

    }

    @Override
    public boolean convertsTo(Type t) {
        return t.actual() instanceof VOID;
    }
}
