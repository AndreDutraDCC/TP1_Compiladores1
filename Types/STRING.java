package Types;

public class STRING extends Type {
    public STRING() {

    }

    @Override
    public boolean convertsTo(Type t) {
        return t.actual() instanceof STRING;
    }
}
