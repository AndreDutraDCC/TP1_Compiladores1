package Types;

public class Array extends Type {
    public Array(Type elementsType) {
        elementsType_ = elementsType;
    }

    @Override
    public boolean convertsTo(Type t) {
        return this == t.actual();
    }
    
    private Type elementsType_;
}
