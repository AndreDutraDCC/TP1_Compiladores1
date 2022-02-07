package Types;

public abstract class Type {
    public Type actual() {
        return this;
    }

    public boolean convertsTo(Type t) {
        return false;
    }
}
