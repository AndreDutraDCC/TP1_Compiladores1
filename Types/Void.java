package Types;

public class Void extends Type {
    public Void() {

    }

    @Override
    public boolean convertsTo(Type t) {
        return t.actual() instanceof Void;
    }
}
