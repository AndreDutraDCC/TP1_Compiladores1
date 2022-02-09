package Types;

public class ARRAY extends Type {
    public ARRAY(Type elementsType) {
        elementsType_ = elementsType;
    }

    @Override
    public boolean convertsTo(Type t) {
        return this == t.actual();
    }
    
    private Type elementsType_;
}
