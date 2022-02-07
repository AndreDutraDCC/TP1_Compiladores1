package Types;

public class Nil extends Type {
    public Nil() {

    }

    @Override
    public boolean convertsTo(Type t) {
        Type destination = t.actual();
        return (destination instanceof Nil) || (destination instanceof Record);
    }
}
