package Types;

public class NIL extends Type {
    public NIL() {

    }

    @Override
    public boolean convertsTo(Type t) {
        Type destination = t.actual();
        return (destination instanceof NIL) || (destination instanceof RECORD);
    }
}
