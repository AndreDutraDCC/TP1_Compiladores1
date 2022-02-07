package Types;

public class Int extends Type {
    public Int() {
    }

    @Override
    public boolean convertsTo(Type t) {
        return t.actual() instanceof Int;
    }
}
