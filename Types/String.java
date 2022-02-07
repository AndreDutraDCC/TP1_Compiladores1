package Types;

public class String extends Type {
    public String() {

    }

    @Override
    public boolean convertsTo(Type t) {
        return t.actual() instanceof String;
    }
}
