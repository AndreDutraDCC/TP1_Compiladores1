package Types;

public class INT extends Type {
    public INT() {
    }

    @Override
    public boolean convertsTo(Type t) {
        return t.actual() instanceof INT;
    }
}
